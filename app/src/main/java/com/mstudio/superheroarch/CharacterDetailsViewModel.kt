package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class CharacterDetailsViewModel(val viewTranslator: CharacterDetailsViewTranslator) : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun onCharacterRetrieved(character: Character?) {
        viewTranslator.showLoader()
        character?.let {
            val firstEpisode = it.episode.first()
            val episodeNumber = firstEpisode.substringAfterLast("/").toInt()
            viewModelScope.launch(Dispatchers.IO) {
                val response = retrofit.create(RickAndMortyDetailsApiService::class.java).getEpisodeDetails(episodeNumber)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val episode = response.body()
                        if (episode != null) {
                            viewTranslator.showEpisodeDetails(episode)
                        } else {
                            viewTranslator.showErrorMessage("Episode not found")
                        }
                    } else {
                        viewTranslator.showErrorMessage(response.errorBody().toString())
                    }
                    viewTranslator.hideLoader()
                }
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}

interface CharacterDetailsViewTranslator {
    fun showEpisodeDetails(episode: Episode)
    fun showErrorMessage(error: String)
    fun showLoader()
    fun hideLoader()

}


interface RickAndMortyDetailsApiService {
    @GET("episode/{id}")
    suspend fun getEpisodeDetails(@Path("id") id: Int): Response<Episode>
}