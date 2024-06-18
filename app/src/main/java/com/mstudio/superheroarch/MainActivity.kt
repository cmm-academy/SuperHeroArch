package com.mstudio.superheroarch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val titleTextView = findViewById<TextView>(R.id.title)
        val button = findViewById<Button>(R.id.main_button)
        val context = this

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRick.getCharacter()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    runOnUiThread {
                        titleTextView.text = responseBody.toString()
                    }
                } else {
                    runOnUiThread {
                        Snackbar.make(findViewById(R.id.main),context.getString(R.string.failed_fetch_data), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

interface ApiRick {
    @GET("character")
    suspend fun getCharacter(): Response<Any>
}
