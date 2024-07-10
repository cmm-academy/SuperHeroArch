package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.model.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    companion object {
        const val EXTRA_CHARACTER = "com.mstudio.superheroarch.MainActivity.EXTRA_CHARACTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val allButton = findViewById<Button>(R.id.all)
        val aliveButton = findViewById<Button>(R.id.alive)
        val deadButton = findViewById<Button>(R.id.dead)
        val unknownButton = findViewById<Button>(R.id.unknown)
        val characterRecyclerView = findViewById<RecyclerView>(R.id.characters_recycler)
        val adapter = CharacterAdapter(this) { character ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER, character)
            startActivity(intent)
        }
        characterRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                adapter.characters = viewState.filteredCharacters
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    is MainViewModel.Event.ShowSnackbar -> {
                        Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                        viewModel.onSnackbarShown()
                    }
                    null -> {}
                }
            }
        }

        allButton.setOnClickListener { viewModel.onStatusClicked(null) }
        aliveButton.setOnClickListener { viewModel.onStatusClicked("Alive") }
        deadButton.setOnClickListener { viewModel.onStatusClicked("Dead") }
        unknownButton.setOnClickListener { viewModel.onStatusClicked("unknown") }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}