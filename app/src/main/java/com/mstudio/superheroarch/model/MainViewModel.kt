package com.mstudio.superheroarch.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.ApiRick
import com.mstudio.superheroarch.ApiService
import com.mstudio.superheroarch.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> get() = _characters

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> get() = _selectedCharacter

    private val _filteredCharactersStatus = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharacters: StateFlow<List<Character>> get() = _filteredCharactersStatus

    private val _showSnackbarEvent = MutableStateFlow(false)
    val showSnackbarEvent: StateFlow<Boolean> get() = _showSnackbarEvent

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRick.getCharacter()
                if (response.isSuccessful) {
                    val characterResponse = response.body()
                    val allCharacters = characterResponse?.results ?: emptyList()

                    withContext(Dispatchers.Main) {
                        _characters.value = allCharacters
                        _filteredCharactersStatus.value = allCharacters
                        if (allCharacters.isEmpty()) {
                            _showSnackbarEvent.value = true
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _showSnackbarEvent.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _showSnackbarEvent.value = true
                }
            }
        }
    }

    fun onStatusClicked(status: String?) {
        _filteredCharactersStatus.value = status?.let {
            _characters.value.filter { it.status.equals(status, ignoreCase = true) }
        } ?: _characters.value
    }

    fun onSnackbarShown() {
        _showSnackbarEvent.value = false
    }

    fun onCharacterClicked(character: Character) {
        _selectedCharacter.value = character
    }
}
