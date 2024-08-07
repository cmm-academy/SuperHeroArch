package com.mstudio.superheroarch.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApiHelper
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: MainViewTranslator,
    private val repository: RickAndMortyRepository = RickAndMortyRepository(api = RickAndMortyApiHelper.create()),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    private var allCharacters = listOf<CharacterData>()

    fun onCreate() {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(dispatcher) {
            try {
                val result = repository.getCharacters()
                withContext(Dispatchers.Main) {
                    val characters = result ?: emptyList()
                    allCharacters = characters.map { it.toCharacterData() }
                    if (characters.isNotEmpty()) {
                        view.showCharacters(allCharacters)
                    } else {
                        view.showEmptyCharactersError()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showGenericError()
                }
            }
        }
    }

    fun onFilterButtonClicked(filter: StatusFilters = StatusFilters.ALL) {
        if (filter != StatusFilters.ALL) {
            view.showCharacters(allCharacters.filter { it.status.equals(filter.status, ignoreCase = true) })
        } else {
            view.showCharacters(allCharacters)
        }
    }

    fun onCharacterClicked(characterSelected: CharacterData) {
        view.goToDetailScreen(characterSelected)
    }
}

interface MainViewTranslator {
    fun showCharacters(characters: List<CharacterData>)
    fun showEmptyCharactersError()
    fun showGenericError()
    fun goToDetailScreen(characterSelected: CharacterData)
}