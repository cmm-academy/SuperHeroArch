package com.mstudio.superheroarch
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.gson.annotations.SerializedName
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
        @GET("character")
        suspend fun getUser(): Response<ApiDataModel>
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    data class ApiDataModel (
        @SerializedName("results") val characterList: List<CharacterData>
    )

    data class CharacterData (
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("image") val image: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.primary_button)
        val text = findViewById<TextView>(R.id.title)
        val name = findViewById<TextView>(R.id.name)
        val status = findViewById<TextView>(R.id.status)
        val image = findViewById<ImageView>(R.id.picture)
        val service = retrofit.create(RetrofitService::class.java)


        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val responseGetUser = service.getUser()
                withContext(Dispatchers.Main) {
                    if (responseGetUser.isSuccessful) {
                        val firstCharacter = responseGetUser.body()?.characterList?.first()
                        if (firstCharacter != null) {
                            name.text = firstCharacter.name
                            status.text = firstCharacter.status
                            image.load(firstCharacter.image)
                            text.text = getString(R.string.one_character_text)
                        } else {
                            name.text = getString(R.string.empty_array)
                        }
                    } else {
                        text.text = getString(R.string.error_api)
                    }
                }
            }
        }
    }
}