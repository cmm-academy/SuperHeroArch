package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        findViewById<Button>(R.id.mainButton).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofit.create(RickAndMortyApiService::class.java).getCharacters()

                if (response.isSuccessful) {
                    val characterList = response.body()
                    val character = characterList?.results?.first()
                    runOnUiThread {
                        findViewById<TextView>(R.id.nameTextView).text = character?.name
                        findViewById<TextView>(R.id.statusTextView).text = character?.status
                        Glide.with(this@MainActivity)
                            .load(character?.image)
                            .into(findViewById(R.id.characterImageView))
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
