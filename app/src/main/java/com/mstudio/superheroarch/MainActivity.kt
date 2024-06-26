package com.mstudio.superheroarch

import android.os.Bundle
import android.view.View
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

    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var apiRick: ApiRick
    private lateinit var adapter: CharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiRick = retrofit.create(ApiRick::class.java)
        val button = findViewById<Button>(R.id.main_button)
        characterRecyclerView = findViewById<RecyclerView>(R.id.characters_recycler)

        adapter = CharacterAdapter()
        characterRecyclerView.adapter = adapter

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRick.getCharacter()

                if (response.isSuccessful) {
                    val characterResponse = response.body()
                    val characters = characterResponse?.results ?: emptyList()

                    withContext(Dispatchers.Main) {
                        if (characters.isNotEmpty()){
                            button.visibility = View.GONE
                        }else{
                            Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                        }
                        adapter.updateCharacters(characters)
                    }
                } else {
                    Snackbar.make(characterRecyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}