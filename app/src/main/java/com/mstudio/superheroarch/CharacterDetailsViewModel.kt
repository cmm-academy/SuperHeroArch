package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(private val viewTranslator: CharacterDetailsViewTranslator) : ViewModel() {
    private val repository = RickAndMortyRepository()

    fun onCharacterRetrieved(character: Character?) {
        viewTranslator.showLoader()
        character?.let {
            val firstEpisode = it.episode.first()
            val episodeNumber = firstEpisode.substringAfterLast("/").toInt()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getEpisodeDetails(episodeNumber)
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
                } catch (e: Exception) {
                    viewTranslator.showErrorMessage(e.message ?: "Unknown error")
                    viewTranslator.hideLoader()
                }
            }
        }
    }
}

interface CharacterDetailsViewTranslator {
    fun showEpisodeDetails(episode: Episode)
    fun showErrorMessage(error: String)
    fun showLoader()
    fun hideLoader()

}
