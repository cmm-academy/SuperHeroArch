package com.mstudio.superheroarch.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding
import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.toCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val adapter = CharactersAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            mainButton.text = resources.getString(R.string.main_button_title)
            charactersRv.adapter = adapter
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
                    result.body()?.characters?.let { characters ->
                        binding.charactersRv.visibility = View.VISIBLE
                        binding.errorBody.visibility = View.GONE
                        adapter.updateItems(characters.map { it.toCharacter() })
                    } ?: {
                        binding.charactersRv.visibility = View.GONE
                        binding.errorBody.visibility = View.VISIBLE
                        binding.errorBody.text = resources.getString(R.string.main_title_empty_body)
                    }

                } else {
                    binding.errorBody.visibility = View.VISIBLE
                    binding.errorBody.text = resources.getString(R.string.main_title_error_body)
                }
            }
        }
    }
}