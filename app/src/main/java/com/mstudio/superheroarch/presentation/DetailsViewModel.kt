package com.mstudio.superheroarch.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.domain.GetEpisodeDetailsUseCase
import com.mstudio.superheroarch.domain.GetEpisodeTMDBDetailsUseCase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val view: DetailsViewTranslator,
    private val getEpisodeDetailsUseCase: GetEpisodeDetailsUseCase,
    private val getEpisodeTMDBDetailsUseCase: GetEpisodeTMDBDetailsUseCase
) : ViewModel() {

    fun fetchCharacterDetails(character: CharacterEntity) {
        view.displayCharacterDetails(character)

        character.firstEpisode.let { episodeUrl ->
            viewModelScope.launch {
                fetchFirstEpisodeDetails(episodeUrl)
            }
        }
    }

    private fun fetchTMDBEpisodeDetails(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            try {
                val episodeTMDBResult = getEpisodeTMDBDetailsUseCase(seasonNumber, episodeNumber)
                episodeTMDBResult?.let {
                    view.displayEpisodeRatingAndImage(it.rating, "") // Asegúrate de agregar la imagen si es necesario
                }
            } catch (e: Exception) {
                view.showError("Failed to load TMDB episode rating: ${e.message}")
            }
        }
    }

    fun extractSeasonAndEpisode(firstEpisode: String): Pair<Int, Int>? {
        return if (firstEpisode.isNotEmpty()) {
            try {
                val season = firstEpisode.substring(1, 3).toInt()   // Extrae el número de temporada
                val episode = firstEpisode.substring(4, 6).toInt()  // Extrae el número de episodio
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

    private suspend fun fetchFirstEpisodeDetails(episodeUrl: String) {
        try {
            val episodeResult = getEpisodeDetailsUseCase(episodeUrl)
            episodeResult.getOrNull()?.let { episodeEntity ->
                view.displayFirstEpisodeDetails(episodeEntity)

                val episodeCode = episodeEntity.episode // Asumimos que este valor tiene el formato "S01E01"

                val seasonAndEpisode = extractSeasonAndEpisode(episodeCode)

                seasonAndEpisode?.let { (season, episode) ->
                    fetchTMDBEpisodeDetails(season, episode)
                } ?: run {
                    view.showError("Invalid episode format")
                }
            }
        } catch (e: Exception) {
            view.showError("Failed to load episode details: ${e.message}")
        }
    }
}

interface DetailsViewTranslator {
    fun displayCharacterDetails(character: CharacterEntity)
    fun displayFirstEpisodeDetails(episode: EpisodeEntity)
    fun displayEpisodeRatingAndImage(rating: Double, imageUrl: String?)
    fun showError(message: String)
}
