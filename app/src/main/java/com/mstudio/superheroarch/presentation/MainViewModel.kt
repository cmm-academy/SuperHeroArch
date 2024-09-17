package com.mstudio.superheroarch.presentation

import androidx.lifecycle.ViewModel
import com.mstudio.superheroarch.domain.FilterCharactersByStatusUseCase
import com.mstudio.superheroarch.domain.GetCharactersUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: ViewTranslator,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val filterCharactersByStatusUseCase: FilterCharactersByStatusUseCase
) : ViewModel() {

    private var allCharacters: List<CharacterEntity> = mutableListOf()
    private var filteredCharacters: List<CharacterEntity> = mutableListOf()

    fun onCreate() {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getCharactersUseCase()

            withContext(Dispatchers.Main) {
                result.onSuccess { characters ->
                    allCharacters = characters
                    filteredCharacters = characters
                    if (allCharacters.isEmpty()) {
                        view.showEmptyError()
                    } else {
                        view.showCharacters(filteredCharacters)
                    }
                }.onFailure {
                    view.showEmptyError()
                }
            }
        }
    }

    fun onCharacterClicked(position: Int) {
        val character = filteredCharacters[position]
        view.navigateToDetails(character)
    }

    fun onFilterClicked(status: String?) {
        filteredCharacters = filterCharactersByStatusUseCase(allCharacters, status)
        view.showCharacters(filteredCharacters)
    }
}

interface ViewTranslator {
    fun showEmptyError()
    fun showCharacters(characters: List<CharacterEntity>)
    fun navigateToDetails(character: CharacterEntity)
}