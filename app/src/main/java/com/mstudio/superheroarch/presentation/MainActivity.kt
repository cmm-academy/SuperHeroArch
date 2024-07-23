package com.mstudio.superheroarch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding
import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            mainButton.text = resources.getString(R.string.main_button_title)
            nameTitle.text = resources.getString(R.string.main_title_not_pressed)
            mainButton.setOnClickListener {
                getCharacters()
            }
        }
    }

    private fun getCharacters() {
        val apiService = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiService.getCharacters()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val firstCharacter = result.body()?.characters?.first()
                    firstCharacter?.let {
                        binding.nameTitle.text = resources.getString(R.string.character_name, it.name)
                        binding.statusTitle.text = resources.getString(R.string.character_status, it.status)
                        binding.characterImage.load(it.image)
                    } ?: {
                        binding.nameTitle.text = resources.getString(R.string.main_title_empty_body)
                    }
                } else {
                    binding.nameTitle.text = resources.getString(R.string.main_title_error_body)
                }
            }
        }
    }
}