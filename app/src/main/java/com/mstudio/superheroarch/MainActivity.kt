package com.mstudio.superheroarch

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.data.Character
import com.mstudio.superheroarch.data.CharactersResponse
import com.mstudio.superheroarch.network.RetrofitInstance
import com.mstudio.superheroarch.network.RickAndMortyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var characterListAdapter: CharacterListAdapter
    lateinit var updateButton: Button
    var dataFromNetwork = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateButton = findViewById(R.id.button_to_change_text)

        val characterListRecyclerView: RecyclerView = findViewById(R.id.character_list)
        characterListRecyclerView.layoutManager = LinearLayoutManager(this)
        characterListAdapter = CharacterListAdapter(mutableListOf())
        characterListRecyclerView.adapter = characterListAdapter

        getDataFromRemote()

        updateButton.setOnClickListener {
            getDataFromRemote()
        }
    }

    private fun getDataFromRemote() {
        var listOfCharacters = emptyList<Character>()
        val apiService = RetrofitInstance.getInstance().create(RickAndMortyApi::class.java)
        val call = apiService.doGetCharacters()
        call.enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(call: Call<CharactersResponse>, response: Response<CharactersResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        for (character in response.body()?.results ?: emptyList()) {
                            listOfCharacters = listOfCharacters + character
                        }
                        characterListAdapter.updateData(listOfCharacters)
                        updateButton.visibility = View.GONE

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