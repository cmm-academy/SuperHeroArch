package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val view: MainViewTranslator) : ViewModel() {

    private val repository = RickAndMortyRepository(RickAndMortyApiFactory.create())
    private val characterList = mutableListOf<Character>()
    private var selectedChipId = R.id.chipAll

    fun onViewCreated() {
        retrieveChars()
    }

    fun onRefreshClicked() {
        retrieveChars(true)
    }

    fun onFilterSelected(checkedChipId: Int) {
        filterAndShowChars(checkedChipId)
    }

    private fun retrieveChars(forceRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characters = repository.getCharacters(forceRefresh)
                withContext(Dispatchers.Main) {
                    updateChars(characters)
                }
            } catch (e: Exception) {
                view.showErrorMessage(e.message ?: "Unknown error")
            }
        }
    }

    private fun updateChars(localChars: List<Character>) {
        characterList.clear()
        characterList.addAll(localChars)
        filterAndShowChars(selectedChipId)
    }

    private fun filterAndShowChars(checkedChipId: Int) {
        when (checkedChipId) {
            R.id.chipAll -> view.showCharacters(characterList)
            R.id.chipAlive -> view.showCharacters(characterList.filter { it.status.equals(ALIVE_STATUS, true) })
            R.id.chipDead -> view.showCharacters(characterList.filter { it.status.equals(DEAD_STATUS, true) })
            R.id.chipUnknown -> view.showCharacters(characterList.filter { it.status.equals(UNKNOWN_STATUS, true) })
        }
        selectedChipId = checkedChipId
    }

    companion object {
        private const val ALIVE_STATUS = "Alive"
        private const val DEAD_STATUS = "Dead"
        private const val UNKNOWN_STATUS = "Unknown"
    }
}

interface MainViewTranslator {
    fun showCharacters(characterList: List<Character>)
    fun showErrorMessage(error: String)

}