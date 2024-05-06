package com.mstudio.superheroarch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter(private val view: MainViewTranslator) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val characterList = mutableListOf<Character>()
    private var selectedChipId = R.id.chipAll

    fun onViewCreated() {
        requestCharacters()
    }

    fun onRefreshClicked() {
        requestCharacters()
    }

    private fun requestCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.create(RickAndMortyApiService::class.java).getCharacters()
            if (response.isSuccessful) {
                val characterListResponse = response.body()
                characterListResponse?.results?.let {
                    characterList.clear()
                    characterList.addAll(it)
                }
                filterAndShowChars(selectedChipId)
            } else {
                view.showErrorMessage("Error: ${response.code()}")
            }
        }
    }

    fun filterAndShowChars(checkedChipId: Int) {
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