package com.mstudio.superheroarch.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: MainViewTranslator,
    private val useCase: GetAllCharactersUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var allCharacters = listOf<CharacterData>()

    fun onCreate() {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(dispatcher) {
            try {
                val result = useCase.getAllCharacters()
                withContext(Dispatchers.Main) {
                    val characters = result ?: emptyList()
                    allCharacters = characters
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

    fun onFavoriteButtonClicked() {
        val favoriteCharacters = allCharacters.filter { it.isFavorite }
        if (favoriteCharacters.isNotEmpty()) {
            view.showCharacters(favoriteCharacters)
        } else {
            view.showEmptyFavoriteCharactersMessage()
        }
    }
}

interface MainViewTranslator {
    fun showCharacters(characters: List<CharacterData>)
    fun showEmptyCharactersError()
    fun showGenericError()
    fun goToDetailScreen(characterSelected: CharacterData)
    fun showEmptyFavoriteCharactersMessage()
}