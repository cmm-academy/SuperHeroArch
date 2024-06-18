package com.mstudio.superheroarch

import android.os.Bundle
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
    var characterNameTitle: TextView? = null
    var characterStatusTitle: TextView? = null

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
        characterNameTitle = findViewById(R.id.character_name)
        characterStatusTitle = findViewById(R.id.character_status)

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
                    val firstCharacter = response.body()?.results?.firstOrNull()
                    firstCharacter?.let {
                        characterNameTitle?.text = getString(R.string.character_name, it.name)
                        characterStatusTitle?.text = getString(R.string.character_status, it.status)
                        Picasso.get().load(it.image).into(findViewById<ImageView>(R.id.character_image));
                    } ?: Snackbar.make(findViewById(R.id.main), getString(R.string.error_message_no_character_info), Snackbar.LENGTH_SHORT).show()

                } else {
                    Snackbar.make(findViewById(R.id.main), getString(R.string.error_message_api_error, response.code().toString()), Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Snackbar.make(findViewById(R.id.main), getString(R.string.error_message_general_failure), Snackbar.LENGTH_SHORT).show()
            }

        })
    }

}