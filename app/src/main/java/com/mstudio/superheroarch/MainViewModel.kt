package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import com.mstudio.superheroarch.ApiService.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val view: ViewTranslator) : ViewModel() {

    private lateinit var apiRick: ApiRick
    private var allCharacters: List<Character> = mutableListOf()
    private var filteredCharacters: List<Character> = mutableListOf()

    fun onCreate() {
        apiRick = retrofit.create(ApiRick::class.java)
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
