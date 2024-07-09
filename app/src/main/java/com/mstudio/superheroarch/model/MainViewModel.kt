package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> get() = _characters

    private val _filteredCharacters = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharacters: StateFlow<List<Character>> get() = _filteredCharacters

    private val _showSnackbarEvent = MutableStateFlow(false)
    val showSnackbarEvent: StateFlow<Boolean> get() = _showSnackbarEvent

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiRick.getCharacter()
            if (response.isSuccessful) {
                val characterResponse = response.body()
                val allCharacters = characterResponse?.results ?: emptyList()

                withContext(Dispatchers.Main) {
                    _characters.value = allCharacters
                    _filteredCharacters.value = allCharacters

                    if (allCharacters.isEmpty()) {
                        _showSnackbarEvent.value = true
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _characters.value = emptyList()
                    _filteredCharacters.value = emptyList()
                    _showSnackbarEvent.value = true
                }
            }
        }
    }

    fun onStatusClicked(status: String?) {
        _filteredCharacters.value = status?.let {
            _characters.value.filter { it.status.equals(status, ignoreCase = true) }
        } ?: _characters.value
    }

    fun onSnackbarShown() {
        _showSnackbarEvent.value = false
    }
}
