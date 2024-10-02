package com.mstudio.superheroarch.presentation

import androidx.lifecycle.ViewModel
import com.mstudio.superheroarch.domain.DataRequest
import com.mstudio.superheroarch.domain.FetchDataUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: ViewTranslator,
    private val fetchDataUseCase: FetchDataUseCase
) : ViewModel() {

    private var allCharacters: List<CharacterEntity> = mutableListOf()
    private var filteredCharacters: List<CharacterEntity> = mutableListOf()

    fun onCreate() {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = fetchDataUseCase(DataRequest.Characters)

            withContext(Dispatchers.Main) {
                result.onSuccess { characters ->
                    if (characters is List<*>) {
                        allCharacters = characters.filterIsInstance<CharacterEntity>()
                        filteredCharacters = allCharacters
                        if (allCharacters.isEmpty()) {
                            view.showEmptyError()
                        } else {
                            view.showCharacters(filteredCharacters)
                        }
                    } else {
                        view.showEmptyError()
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
    fun showCharacters(characters: List<CharacterEntity>)
    fun navigateToDetails(character: CharacterEntity)
}
