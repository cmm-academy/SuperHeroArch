package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel( private val view: ViewTranslator, private val repository: RickAndMortyRepository) : ViewModel() {

    private var allCharacters: List<Character> = mutableListOf()
    private var filteredCharacters: List<Character> = mutableListOf()

    fun onCreate() {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.fetchCharacters()
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
        filteredCharacters = if (status == null) {
            allCharacters
        } else {
            allCharacters.filter { it.status.equals(status, ignoreCase = true) }
        }
        view.showCharacters(filteredCharacters)
    }
}

interface ViewTranslator {
    fun showEmptyError()
    fun showCharacters(characters: List<Character>)
    fun navigateToDetails(character: Character)
}