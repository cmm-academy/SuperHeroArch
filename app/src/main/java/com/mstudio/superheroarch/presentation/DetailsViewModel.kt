package com.mstudio.superheroarch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.domain.GetEpisodeDetailsUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val getEpisodeDetailsUseCase: GetEpisodeDetailsUseCase): ViewModel() {

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
            val episodeResult = getEpisodeDetailsUseCase(episodeUrl)
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
