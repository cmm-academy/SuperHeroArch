package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(private val viewTranslator: CharacterDetailsViewTranslator) : ViewModel() {
    private val repository = RickAndMortyRepository()
    private val tmdbRepository = TMDBRepository()

    fun onCharacterRetrieved(character: Character?) {
        viewTranslator.showLoader()
        character?.let {
            val firstEpisode = it.episode.first()
            val episodeNumber = firstEpisode.substringAfterLast("/").toInt()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getEpisodeDetails(episodeNumber)
                    withContext(Dispatchers.Main) {
                        viewTranslator.showEpisodeDetails(response)
                    }
                    getRating(response.episodeNumber)
                } catch (e: Exception) {
                    viewTranslator.showErrorMessage(e.message ?: "Unknown error")
                    viewTranslator.hideLoader()
                }
            }
        }
    }

    private fun getRating(seasonAndEpisode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val regex = Regex("S(\\d+)E(\\d+)")
                val matchResult = regex.find(seasonAndEpisode)
                val season = matchResult?.groups?.get(1)?.value?.toInt()
                val episode = matchResult?.groups?.get(2)?.value?.toInt()
                val episodeRating = tmdbRepository.getEpisodeDetails(season!!, episode!!)
                withContext(Dispatchers.Main) {
                    viewTranslator.showEpisodeDetails(episodeRating)
                    viewTranslator.hideLoader()
                }
            } catch (e: Exception) {
                viewTranslator.showErrorMessage(e.message ?: "Unknown error")
                viewTranslator.hideLoader()
            }
        }
    }
}

interface CharacterDetailsViewTranslator {
    fun showEpisodeDetails(episode: Episode)
    fun showEpisodeDetails(episode: TMDBEpisodeData)
    fun showErrorMessage(error: String)
    fun showLoader()
    fun hideLoader()
}
