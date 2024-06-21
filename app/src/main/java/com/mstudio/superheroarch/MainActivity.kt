package com.mstudio.superheroarch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
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

        val apiRick = retrofit.create(ApiRick::class.java)
        val button = findViewById<Button>(R.id.main_button)
        val recyclerView = findViewById<RecyclerView>(R.id.characters_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)


        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRick.getCharacter()

                if (response.isSuccessful) {
                    val characterResponse = response.body()
                    val characters = characterResponse?.results ?: emptyList()

                    withContext(Dispatchers.Main) {
                        recyclerView.adapter = CharacterAdapter(characters)
                    }
                }else{
                    Snackbar.make(recyclerView, R.string.failed_fetch_data, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}

data class Character(
    val name: String,
    val status: String,
    val image: String
)

data class CharacterResponse(
    val results: List<Character>
)

interface ApiRick {
    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>
}

    class CharacterAdapter(private val characters: List<Character>) :
        RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

        class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.character_name)
            val status: TextView = view.findViewById(R.id.character_status)
            val image: ImageView = view.findViewById(R.id.character_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
            return CharacterViewHolder(view)
        }

        override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
            val character = characters[position]
            holder.name.text = character.name
            holder.status.text = character.status

            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = Picasso.get().load(character.image).get()
                withContext(Dispatchers.Main) {
                holder.image.setImageBitmap(bitmap)
                }
            }
        }

        override fun getItemCount(): Int = characters.size
    }

