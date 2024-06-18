package com.mstudio.superheroarch

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.data.CharactersResponse
import com.mstudio.superheroarch.network.RetrofitInstance
import com.mstudio.superheroarch.network.RickAndMortyApi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var characterNameTitle: TextView
    lateinit var characterStatusTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.button_to_change_text)
        button.setOnClickListener {
            getDataFromRemote()
        }
    }

    private fun getDataFromRemote() {
        val apiService = RetrofitInstance.getInstance().create(RickAndMortyApi::class.java)
        val call = apiService.doGetCharacters()
        call.enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(call: Call<CharactersResponse>, response: Response<CharactersResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    if (results?.isNotEmpty() == true) {
                        val character = results[0]

                        characterNameTitle = findViewById(R.id.character_name)
                        val characterNameData = getString(R.string.character_name) + character.name
                        characterNameTitle.text = characterNameData

                        characterStatusTitle = findViewById(R.id.character_status)
                        val characterStatusData = getString(R.string.character_status) + character.status
                        characterStatusTitle.text = characterStatusData

                        Picasso.get().load(character.image).into(findViewById<ImageView>(R.id.character_image));
                        Log.d("Successful response", response.body().toString())
                    } else {
                        Snackbar.make(findViewById(R.id.main), "No characters info", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(findViewById(R.id.main), "No backend response", Snackbar.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Snackbar.make(findViewById(R.id.main), "Some failure", Snackbar.LENGTH_SHORT).show()
            }

        })
    }

}