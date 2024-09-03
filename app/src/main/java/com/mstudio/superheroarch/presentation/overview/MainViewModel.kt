package com.mstudio.superheroarch.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.usecase.GetAllCharactersUseCase
import com.mstudio.superheroarch.usecase.GetFavCharactersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: MainViewTranslator,
    private val useCase: GetAllCharactersUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val getFavCharactersUseCase: GetFavCharactersUseCase
) : ViewModel() {

    private var allCharacters = listOf<CharacterData>()

    fun retrieveCharacters() {
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

    fun onFavButtonClicked() {
        viewModelScope.launch(dispatcher) {
            val result = getFavCharactersUseCase.getFavCharacters()

            withContext(Dispatchers.Main) {
                if (result.isNotEmpty()) {
                    view.showCharacters(result)
                } else {
                    view.showEmptyFavCharactersMessage()
                }
            }
        }
    }
}

interface MainViewTranslator {
    fun showCharacters(characters: List<CharacterData>)
    fun showEmptyCharactersError()
    fun showGenericError()
    fun goToDetailScreen(characterSelected: CharacterData)
    fun showEmptyFavCharactersMessage()
}