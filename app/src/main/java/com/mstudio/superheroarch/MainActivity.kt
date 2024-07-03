package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.ApiService.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val adapter = CharacterAdapter()
    private lateinit var apiRick: ApiRick
    private var allCharacters: List<Character> = emptyList() // Lista para mantener todos los personajes

    companion object {
        const val EXTRA_CHARACTER = "com.mstudio.superheroarch.MainActivity.EXTRA_CHARACTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        apiRick = retrofit.create(ApiRick::class.java)

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

        allButton.setOnClickListener {filterCharacters(null)}
        aliveButton.setOnClickListener {filterCharacters("Alive")}
        deadButton.setOnClickListener {filterCharacters("Dead")}
        unknownButton.setOnClickListener {filterCharacters("unknown")}

        CoroutineScope(Dispatchers.IO).launch{
            val response = apiRick.getCharacter()

            if (response.isSuccessful) {
                val characterResponse = response.body()
                allCharacters = characterResponse?.results ?: emptyList()

                withContext(Dispatchers.Main) {
                    if (allCharacters.isEmpty()) {
                        Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                    }
                    adapter.updateCharacters(allCharacters)
                }
            } else{
                withContext(Dispatchers.Main){
                    Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun filterCharacters(status: String?) {
        adapter.filterCharactersByStatus(status)
    }
}
