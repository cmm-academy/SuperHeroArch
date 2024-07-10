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

    data class ViewState(
        val characters: List<Character> = emptyList(),
        val filteredCharacters: List<Character> = emptyList()
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> get() = _event

    sealed class Event {
        object ShowSnackbar : Event()
    }

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
                    _viewState.value = _viewState.value.copy(
                        characters = allCharacters,
                        filteredCharacters = allCharacters
                    )

                    if (allCharacters.isEmpty()) {
                        _event.value = Event.ShowSnackbar
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _viewState.value = _viewState.value.copy(
                        characters = emptyList(),
                        filteredCharacters = emptyList()
                    )
                    _event.value = Event.ShowSnackbar
                }
            }
        }
    }

    fun onStatusClicked(status: String?) {
        val filtered = status?.let {
            _viewState.value.characters.filter { it.status.equals(status, ignoreCase = true) }
        } ?: _viewState.value.characters

        _viewState.value = _viewState.value.copy(filteredCharacters = filtered)
    }

    fun onSnackbarShown() {
        _event.value = null
    }
}