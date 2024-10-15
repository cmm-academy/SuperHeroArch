package com.mstudio.superheroarch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.domain.GetEpisodeAndDetailsUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val getEpisodeAndDetailsUseCase: GetEpisodeAndDetailsUseCase
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
        val result = getEpisodeAndDetailsUseCase(episodeUrl)
        result.onSuccess { episodeDetails ->
            if (episodeDetails != null) {
                view.displayEpisodeDetails(null, episodeDetails)
            } else {
                view.showError("Failed to load episode details")
            }
        }.onFailure {
            view.showError("Failed to load episode details")
        }
    }
}

interface DetailsViewTranslator {
    fun displayCharacterDetails(character: CharacterEntity)
    fun displayEpisodeDetails(
        episodeEntity: EpisodeEntity?,
        episodeDetailsViewEntity: EpisodeDetailsViewEntity?
    )

    fun showError(message: String)
}