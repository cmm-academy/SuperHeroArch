package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setClickListener()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        characterAdapter = CharacterAdapter { character ->
            val intent = Intent(this, CharacterDetailsActivity::class.java)
            intent.putExtra("character", character)
            startActivity(intent)
        }
        recyclerView.adapter = characterAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setClickListener() {
        val myButton = findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            it.visibility = View.GONE
            getCharacters()
        }
    }

    private fun getCharacters() {
        lifecycleScope.launch {
            try {
                val response = RetroFitClient.apiService.getCharacters()
                characterAdapter.submitList(response.results)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
