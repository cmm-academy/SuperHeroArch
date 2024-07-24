package com.mstudio.superheroarch.presentation.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding
import com.mstudio.superheroarch.presentation.detail.CharacterDetailActivity
import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        const val CHARACTER_DATA_KEY = "character"
    }

    private lateinit var binding: MainActivityBinding
    private val adapter = CharactersAdapter {
        goToDetail(it)
    }
    private var allCharacters = listOf<CharactersRemoteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            charactersRv.adapter = adapter
            getCharacters()
            aliveFilterButton.text = resources.getString(R.string.alive_filter_button_title)
            deadFilterButton.text = resources.getString(R.string.dead_filter_button_title)
            unknownFilterButton.text = resources.getString(R.string.unknown_filter_button_title)
            allFilterButton.text = resources.getString(R.string.all_filter_button_title)
            aliveFilterButton.setOnClickListener {
                setCharacterFilter(allCharacters, StatusFilters.ALIVE)
            }

            deadFilterButton.setOnClickListener {
                setCharacterFilter(allCharacters, StatusFilters.DEAD)
            }

            unknownFilterButton.setOnClickListener {
                setCharacterFilter(allCharacters, StatusFilters.UNKNOWN)
            }

            allFilterButton.setOnClickListener {
                setCharacterFilter(allCharacters, StatusFilters.ALL)
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
                        allCharacters = characters
                        adapter.submitList(characters)
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

    private fun goToDetail(character: CharactersRemoteEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra(CHARACTER_DATA_KEY, character)
        }
        startActivity(intent)
    }

    private fun setCharacterFilter(allCharacters: List<CharactersRemoteEntity>, filter: StatusFilters) {
        if (filter != StatusFilters.ALL) {
            adapter.submitList(allCharacters.filter { it.status.equals(filter.status, ignoreCase = true) })
        } else {
            adapter.submitList(allCharacters)
        }
    }
}