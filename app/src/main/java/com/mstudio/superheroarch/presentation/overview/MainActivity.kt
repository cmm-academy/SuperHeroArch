package com.mstudio.superheroarch.presentation.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding
import com.mstudio.superheroarch.localdatasource.DatabaseHelper
import com.mstudio.superheroarch.presentation.detail.CharacterDetailActivity
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApiHelper
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), MainViewTranslator {

    companion object {
        const val CHARACTER_DATA_KEY = "character"
    }

    private lateinit var binding: MainActivityBinding
    private val viewModel: MainViewModel by lazy {
        MainViewModel(
            this,
            GetAllCharactersUseCase(RickAndMortyRepository(DatabaseHelper.create(), RickAndMortyApiHelper.create())),
            Dispatchers.IO
        )
    }
    private val adapter = CharactersAdapter {
        viewModel.onCharacterClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setUpListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onCreate()
    }

    private fun setUpView() {
        with(binding) {
            charactersRv.adapter = adapter
            aliveFilterButton.text = resources.getString(R.string.alive_filter_button_title)
            deadFilterButton.text = resources.getString(R.string.dead_filter_button_title)
            unknownFilterButton.text = resources.getString(R.string.unknown_filter_button_title)
            allFilterButton.text = resources.getString(R.string.all_filter_button_title)
            favoritesButton.text = resources.getString(R.string.favorites_button_title)
        }
    }

    private fun setUpListeners() {
        with(binding) {
            aliveFilterButton.setOnClickListener {
                viewModel.onFilterButtonClicked(StatusFilters.ALIVE)
            }

            deadFilterButton.setOnClickListener {
                viewModel.onFilterButtonClicked(StatusFilters.DEAD)
            }

            unknownFilterButton.setOnClickListener {
                viewModel.onFilterButtonClicked(StatusFilters.UNKNOWN)
            }

            allFilterButton.setOnClickListener {
                viewModel.onFilterButtonClicked()
            }

            favoritesButton.setOnClickListener {
                viewModel.onFavoriteButtonClicked()
            }
        }
    }

    override fun showCharacters(characters: List<CharacterData>) {
        binding.charactersRv.visibility = View.VISIBLE
        binding.errorBody.visibility = View.GONE
        adapter.submitList(characters)
    }

    override fun showEmptyCharactersError() {
        binding.charactersRv.visibility = View.GONE
        binding.errorBody.visibility = View.VISIBLE
        binding.errorBody.text = resources.getString(R.string.main_title_empty_body)
    }

    override fun showGenericError() {
        binding.errorBody.visibility = View.VISIBLE
        binding.errorBody.text = resources.getString(R.string.main_title_error_body)
    }

    override fun goToDetailScreen(characterSelected: CharacterData) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra(CHARACTER_DATA_KEY, characterSelected)
        }
        startActivity(intent)
    }

    override fun showEmptyFavCharactersMessage() {
        binding.charactersRv.visibility = View.GONE
        binding.errorBody.visibility = View.VISIBLE
        binding.errorBody.text = resources.getString(R.string.favorite_characters_empty_message)
    }
}