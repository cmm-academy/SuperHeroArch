package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter(private val view: MainViewTranslator) : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val characterList = mutableListOf<Character>()
    private var selectedChipId = R.id.chipAll

    fun onViewCreated() {
        retrieveChars()
    }

    fun onRefreshClicked() {
        retrieveChars()
    }

    private fun retrieveChars() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofit.create(RickAndMortyApiService::class.java).getCharacters()
                if (response.isSuccessful) {
                    characterList.clear()
                    characterList.addAll(response.body()?.results ?: emptyList())
                    filterAndShowChars(selectedChipId)
                } else {
                    view.showErrorMessage(response.errorBody().toString())
                }
            } catch (e: Exception) {
                view.showErrorMessage(e.message ?: "Unknown error")
            }
        }
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
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
        private const val ALIVE_STATUS = "Alive"
        private const val DEAD_STATUS = "Dead"
        private const val UNKNOWN_STATUS = "Unknown"
    }
}

interface MainViewTranslator {
    fun showCharacters(characterList: List<Character>)
    fun showErrorMessage(error: String)

}