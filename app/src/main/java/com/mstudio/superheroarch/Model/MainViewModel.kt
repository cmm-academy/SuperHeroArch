package com.mstudio.superheroarch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _filteredCharacters = MutableLiveData<List<Character>>()
    val filteredCharacters: LiveData<List<Character>> get() = _filteredCharacters

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
                }
            } else {
                withContext(Dispatchers.Main) {
                    _characters.value = emptyList()
                    _filteredCharacters.value = emptyList()
                }
            }
        }
    }

    fun filterCharactersByStatus(status: String?) {
        _filteredCharacters.value = status?.let {
            _characters.value?.filter { it.status.equals(status, ignoreCase = true) }
        } ?: _characters.value
    }
}
