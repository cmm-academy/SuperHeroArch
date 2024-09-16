package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val repository: RickAndMortyRepository
) : ViewModel() {

    fun fetchCharacterDetails(character: CharacterEntity) {
        view.displayCharacterDetails(character)
        character.firstEpisode.let { episodeUrl ->
            viewModelScope.launch {
                fetchFirstEpisodeDetails(episodeUrl)
            }
        }
    }

    private suspend fun fetchFirstEpisodeDetails(episodeUrl: String) {
        try {
            val episodeResult = repository.fetchEpisodeDetails(episodeUrl)
            episodeResult.getOrNull()?.let {
                view.displayFirstEpisodeDetails(it)
            }
        } catch (e: Exception) {
            view.showError("Failed to load episode details")
        }
    }
}

interface DetailsViewTranslator {
    fun displayCharacterDetails(character: CharacterEntity)
    fun displayFirstEpisodeDetails(episode: EpisodeEntity)
    fun showError(message: String)
}
