package com.mstudio.superheroarch.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.domain.FetchDataUseCase
import com.mstudio.superheroarch.domain.DataRequest
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val fetchDataUseCase: FetchDataUseCase
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
        viewModelScope.launch {
            val episodeResult = fetchDataUseCase(DataRequest.EpisodeDetails(episodeUrl))
            episodeResult.onSuccess { episodeEntity ->
                val episode = episodeEntity as EpisodeEntity
                view.displayFirstEpisodeDetails(episode)

                val seasonAndEpisode = extractSeasonAndEpisode(episode.episode)
                seasonAndEpisode?.let { (season, episodeNumber) ->
                    fetchTMDBEpisodeDetails(season, episodeNumber)
                } ?: run {
                    view.showError("Invalid episode format")
                }
            }.onFailure {
                view.showError("Failed to load episode details: ${it.message}")
            }
        }
    }

    private suspend fun fetchTMDBEpisodeDetails(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            val tmdbResult = fetchDataUseCase(DataRequest.TmdbEpisodeDetails(seasonNumber, episodeNumber))
            tmdbResult.onSuccess { tmdbDetails ->
                val episodeTMDB = tmdbDetails as EpisodeTMDBInfoEntity
                val imageUrl = "https://image.tmdb.org/t/p/w500${episodeTMDB.imageUrl}"
                view.displayEpisodeRatingAndImage(episodeTMDB.rating, imageUrl)
            }.onFailure {
                view.showError("Failed to load TMDB episode details: ${it.message}")
            }
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