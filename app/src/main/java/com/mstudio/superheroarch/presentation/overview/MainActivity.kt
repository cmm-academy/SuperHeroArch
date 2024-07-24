package com.mstudio.superheroarch.presentation.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding
import com.mstudio.superheroarch.presentation.detail.CharacterDetailActivity
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity

class MainActivity : AppCompatActivity(), MainViewTranslator {

    companion object {
        const val CHARACTER_DATA_KEY = "character"
    }

    private lateinit var binding: MainActivityBinding
    private val viewModel: MainViewModel by lazy { MainViewModel(this) }
    private val adapter = CharactersAdapter {
        viewModel.onCharacterClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.onCreate()
        setUpView()
        setUpListeners()
    }

    private fun setUpView() {
        with(binding) {
            charactersRv.adapter = adapter
            aliveFilterButton.text = resources.getString(R.string.alive_filter_button_title)
            deadFilterButton.text = resources.getString(R.string.dead_filter_button_title)
            unknownFilterButton.text = resources.getString(R.string.unknown_filter_button_title)
            allFilterButton.text = resources.getString(R.string.all_filter_button_title)
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
        }
    }

    override fun showCharacters(characters: List<CharactersRemoteEntity>) {
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

    override fun goToDetailScreen(characterSelected: CharactersRemoteEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra(CHARACTER_DATA_KEY, characterSelected)
        }
        startActivity(intent)
    }
}