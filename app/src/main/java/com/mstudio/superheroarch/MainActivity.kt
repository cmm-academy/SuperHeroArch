package com.mstudio.superheroarch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private val adapter = CharactersAdapter()
    private val characterList = mutableListOf<Character>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<RecyclerView>(R.id.characterList).adapter = adapter
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        chipGroup.setOnCheckedStateChangeListener { group, _ ->
            when (group.checkedChipId) {
                R.id.chipAll -> adapter.setCharacters(characterList)
                R.id.chipAlive -> adapter.setCharacters(characterList.filter { it.status.equals("Alive", true) })
                R.id.chipDead -> adapter.setCharacters(characterList.filter { it.status.equals("Dead", true) })
                R.id.chipUnknown -> adapter.setCharacters(characterList.filter { it.status.equals("Unknown", true) })
            }
        }

        adapter.setItemClickedListener {
            startActivity(CharacterDetailsActivity.newIntent(this, it))
        }

        requestCharacters()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            requestCharacters()
        }
    }

    private fun requestCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.create(RickAndMortyApiService::class.java).getCharacters()
            if (response.isSuccessful) {
                val characterListResponse = response.body()
                characterListResponse?.results?.let { characterList.addAll(it) }
                runOnUiThread {
                    adapter.setCharacters(characterListResponse?.results ?: emptyList())
                }
            } else {
                Snackbar.make(findViewById(R.id.main), "Error: ${response.code()}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): Response<ApiResponseWrapper>
}
