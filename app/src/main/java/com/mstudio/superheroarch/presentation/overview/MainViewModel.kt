package com.mstudio.superheroarch.presentation.overview

import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: MainViewTranslator
) {

    private val repository = RickAndMortyRepository()
    private var allCharacters = listOf<CharactersRemoteEntity>()

    fun onCreate() {
        getCharacters()
    }

    private fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.getCharacters()
                withContext(Dispatchers.Main) {
                    val characters = result ?: emptyList()
                    allCharacters = characters
                    if (characters.isNotEmpty()) {
                        view.showCharacters(characters)
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

    fun onCharacterClicked(characterSelected: CharactersRemoteEntity) {
        view.goToDetailScreen(characterSelected)
    }
}

interface MainViewTranslator {
    fun showCharacters(characters: List<CharactersRemoteEntity>)
    fun showEmptyCharactersError()
    fun showGenericError()
    fun goToDetailScreen(characterSelected: CharactersRemoteEntity)
}