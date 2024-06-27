package com.mstudio.superheroarch
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    interface RetrofitService {
        @GET("characters∫")
        suspend fun getUser(): Response<JsonObject>
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.primary_button)
        val text = findViewById<TextView>(R.id.title)
        val service = retrofit.create(RetrofitService::class.java)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val responseGetUser = service.getUser()
                withContext(Dispatchers.Main) {
                    if (responseGetUser.isSuccessful) {
                        text.text = responseGetUser.body().toString()
                    } else {
                        text.text = getString(R.string.error_api)
                    }
                }
            }
        }
    }
}