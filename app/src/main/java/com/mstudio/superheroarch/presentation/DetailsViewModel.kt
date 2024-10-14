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
        try {
            val result = getEpisodeAndDetailsUseCase(episodeUrl)
            if (result.isSuccess) {
                val episodeDetailsViewEntity = result.getOrThrow()

                val episodeEntity: EpisodeEntity? = null
                view.displayEpisodeDetails(episodeEntity, episodeDetailsViewEntity)
            } else {
                view.showError("Failed to load episode details")
            }
        } catch (e: Exception) {
            view.showError("Failed to load episode details: ${e.message}")
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