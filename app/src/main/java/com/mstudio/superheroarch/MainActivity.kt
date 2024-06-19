package com.mstudio.superheroarch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        val name = findViewById<TextView>(R.id.character_name)
        val status = findViewById<TextView>(R.id.character_status)
        val button = findViewById<Button>(R.id.main_button)
        val context = this
        val imageView = findViewById<ImageView>(R.id.character_image)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRick.getCharacter()

                if (response.isSuccessful) {
                    val characterResponse = response.body()
                    val firstCharacter = characterResponse?.results?.firstOrNull()

                    firstCharacter?.let { character ->
                        withContext(Dispatchers.Main) {
                            name.text =  context.getString(R.string.name) + " " + character.name
                            status.text = context.getString(R.string.status) + " " + character.status

                            val imageUrl = character.image
                            Picasso.get().load(imageUrl).into(imageView)
                        }
                    } ?: run {
                        name.text = context.getString(R.string.no_character_found)
                        status.visibility = View.GONE
                        imageView.setImageResource(R.drawable.image_not_found)
                    }
                } else {
                    withContext(Dispatchers.Main) {Snackbar.make(findViewById(R.id.main), context.getString(R.string.failed_fetch_data), Snackbar.LENGTH_LONG).show() }
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
}

