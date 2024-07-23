package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import com.mstudio.superheroarch.ApiService.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val view: ViewTranslator) : ViewModel() {

    private val apiRick: ApiRick = retrofit.create(ApiRick::class.java)
    private var allCharacters: List<Character> = mutableListOf()
    private var filteredCharacters: List<Character> = mutableListOf()

    fun onCreate() {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiRick.getCharacter()

            if (response.isSuccessful) {
                val characterResponse = response.body()
                allCharacters = characterResponse?.results?.toMutableList() ?: mutableListOf()

                withContext(Dispatchers.Main) {
                    if (allCharacters.isEmpty()) {
                        view.showEmptyError()
                    } else {
                        filteredCharacters = allCharacters
                        view.showCharacters(filteredCharacters)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.showEmptyError()
                }
            }
        }
    }

    fun onCharacterClicked(position: Int) {
        val character = filteredCharacters[position]
        fetchFirstEpisode(character)
    }
    private fun fetchFirstEpisode(character: Character) {
        CoroutineScope(Dispatchers.IO).launch {
            val firstEpisodeUrl = character.episode.firstOrNull()
            if (firstEpisodeUrl != null) {
                val response = apiRick.getEpisode(firstEpisodeUrl)
                if (response.isSuccessful) {
                    val episode = response.body()
                    withContext(Dispatchers.Main) {
                        view.navigateToDetails(character, episode)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view.showEmptyError()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.navigateToDetails(character, null)
                }
            }
        }
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
    fun navigateToDetails(character: Character, episode: Episode?)
}
