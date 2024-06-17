package com.mstudio.superheroarch

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mstudio.superheroarch.data.CharactersResponse
import com.mstudio.superheroarch.network.RetrofitInstance
import com.mstudio.superheroarch.network.RickAndMortyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var resultFromRemote = ""

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
        val titleToChange = findViewById<TextView>(R.id.title_to_change)
        getDataFromRemote()
        button.setOnClickListener {
            titleToChange.text = resultFromRemote
        }
    }

    private fun getDataFromRemote() {
        val apiService = RetrofitInstance.getInstance().create(RickAndMortyApi::class.java)
        val call = apiService.doGetCharacters()
        call.enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(call: Call<CharactersResponse>, response: Response<CharactersResponse>) {
                if (response.isSuccessful) {
                    resultFromRemote = response.body()?.info.toString()
                    Log.d("Successful response", response.body().toString())
                } else {
                    Log.d("Not successful response", response.code().toString())
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Log.d("Big Fail", t.toString())
            }

        })
    }

}