package com.mstudio.superheroarch.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.domain.UnificatedUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val unificatedUseCase: UnificatedUseCase
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
            val result = unificatedUseCase(episodeUrl)
            if (result.isSuccess) {
                val (episodeEntity, tmdbInfo) = result.getOrThrow()
                view.displayFirstEpisodeDetails(episodeEntity)

                val seasonAndEpisode = extractSeasonAndEpisode(episodeEntity.episode)

                seasonAndEpisode?.let { (season, episode) ->
                    view.displayEpisodeRatingAndImage(tmdbInfo.rating, "https://image.tmdb.org/t/p/w500" + tmdbInfo.imageUrl)
                } ?: run {
                    view.showError("Invalid episode format")
                }
            } else {
                view.showError("Failed to load episode details")
            }
        } catch (e: Exception) {
            view.showError("Failed to load episode details: ${e.message}")
        }
    }

    fun extractSeasonAndEpisode(firstEpisode: String): Pair<Int, Int>? {
        return if (firstEpisode.isNotEmpty()) {
            try {
                val season = firstEpisode.substring(1, 3).toInt()
                val episode = firstEpisode.substring(4, 6).toInt()
                Pair(season, episode)
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error parsing season and episode: ${e.message}")
                null
            }
        } else {
            Log.e("DetailsViewModel", "Error: firstEpisode string is empty")
            null
        }
    }
}

interface DetailsViewTranslator {
    fun displayCharacterDetails(character: CharacterEntity)
    fun displayFirstEpisodeDetails(episode: EpisodeEntity)
    fun displayEpisodeRatingAndImage(rating: Double, imageUrl: String?)
    fun showError(message: String)
}
