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

        val adapter = CharacterAdapter { character ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER, character)
            startActivity(intent)
        }
        characterRecyclerView.adapter = adapter

        allButton.setOnClickListener { viewModel.onStatusClicked(null) }
        aliveButton.setOnClickListener { viewModel.onStatusClicked("Alive") }
        deadButton.setOnClickListener { viewModel.onStatusClicked("Dead") }
        unknownButton.setOnClickListener { viewModel.onStatusClicked("unknown") }

        lifecycleScope.launch {
            viewModel.filteredCharacters.collect { filteredCharacters ->
                adapter.characters = filteredCharacters
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.showSnackbarEvent.collect { showSnackbar ->
                if (showSnackbar) {
                    Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                    viewModel.onSnackbarShown()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
