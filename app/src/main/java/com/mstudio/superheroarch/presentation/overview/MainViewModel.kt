package com.mstudio.superheroarch.presentation.overview

import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val view: MainViewTranslator
) {

    private var allCharacters = listOf<CharactersRemoteEntity>()

    fun onCreate() {
        getCharacters()
    }

    private fun getCharacters() {
        val apiService = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiService.getCharacters()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    result.body()?.characters?.let { characters ->
                        allCharacters = characters
                        view.showCharacters(characters)
                    } ?: {
                        view.showEmptyCharactersError()
                    }

                } else {
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