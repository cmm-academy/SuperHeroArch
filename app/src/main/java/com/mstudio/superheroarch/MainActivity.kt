package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    private val adapter = CharactersAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrieveCharsButton = findViewById<Button>(R.id.mainButton)
        findViewById<RecyclerView>(R.id.characterList).adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrieveCharsButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofit.create(RickAndMortyApiService::class.java).getCharacters()

                if (response.isSuccessful) {
                    val characterList = response.body()
                    runOnUiThread {
                        adapter.setCharacters(characterList?.results ?: emptyList())
                        if(characterList?.results.isNullOrEmpty().not()){
                            retrieveCharsButton.visibility = Button.GONE
                        }
                    }
                } else {
                    Snackbar.make(findViewById(R.id.main), "Error: ${response.code()}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): Response<ApiResponseWrapper>
}
