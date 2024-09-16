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

    fun onStart() {
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
                        view.showEmptyCharactersError(false)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showGenericError()
                }
            }
        }
    }

    fun onFilterButtonClicked(filter: CharactersFilters = CharactersFilters.ALL) {
        when (filter) {
            CharactersFilters.ALL -> view.showCharacters(allCharacters)
            CharactersFilters.ALIVE, CharactersFilters.DEAD, CharactersFilters.UNKNOWN -> {
                view.showCharacters(allCharacters.filter { it.status.equals(filter.type, ignoreCase = true) })
            }

            CharactersFilters.FAVORITES -> {
                val favoriteCharacters = allCharacters.filter { it.isFavorite }
                if (favoriteCharacters.isNotEmpty()) {
                    view.showCharacters(favoriteCharacters)
                } else {
                    view.showEmptyCharactersError(true)
                }
            }
        }
    }

    fun onCharacterClicked(characterSelected: CharacterData) {
        view.goToDetailScreen(characterSelected)
    }
}

interface MainViewTranslator {
    fun showCharacters(characters: List<CharacterData>)
    fun showEmptyCharactersError(isFavoriteSection: Boolean = false)
    fun showGenericError()
    fun goToDetailScreen(characterSelected: CharacterData)
}