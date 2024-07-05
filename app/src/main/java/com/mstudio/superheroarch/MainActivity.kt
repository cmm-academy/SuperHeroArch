package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val adapter = CharacterAdapter()
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val characterRecyclerView = findViewById<RecyclerView>(R.id.characters_recycler)
        characterRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val character = adapter.characters[position]
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra(EXTRA_CHARACTER, character)
                startActivity(intent)
            }
        })

        allButton.setOnClickListener { viewModel.filterCharactersByStatus(null) }
        aliveButton.setOnClickListener { viewModel.filterCharactersByStatus("Alive") }
        deadButton.setOnClickListener { viewModel.filterCharactersByStatus("Dead") }
        unknownButton.setOnClickListener { viewModel.filterCharactersByStatus("unknown") }

        viewModel.characters.observe(this, Observer { characters ->
            if (characters.isEmpty()) {
                Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.filteredCharacters.observe(this, Observer { filteredCharacters ->
            adapter.updateCharacters(filteredCharacters)
        })
    }
}
