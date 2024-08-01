package com.mstudio.superheroarch.presentation.detail

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.model.Episode
import com.mstudio.superheroarch.presentation.model.TheMovieDbEpisode
import com.mstudio.superheroarch.presentation.network.NetworkManager
import com.mstudio.superheroarch.remotedatasource.model.toEpisode
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEpisode
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TheMovieDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator
) {

    private val repository = RickAndMortyRepository()
    private val theMovieDbRepository = TheMovieDbRepository()

    fun onCharacterReceived(character: CharacterData) {
        if (NetworkManager.isInternetConnection()) {
            getFirstEpisode(character)
        } else {
            view.showNoInternetConnection()
        }
    }

    private fun getFirstEpisode(character: CharacterData) {
        val firstEpisode = character.episodes.first().split("/").last()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getSingleEpisode(firstEpisode.toInt())
                withContext(Dispatchers.Main) {
                    view.showEpisode(response.toEpisode())
                }
                getRatingAndImageEpisode(response.episode)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showEpisodeError()
                }
            }
        }
    }

    private fun getRatingAndImageEpisode(episodeNumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val seasonPattern = Regex("S([0-9.]+)")
                val seasonMatch = seasonPattern.find(episodeNumber)
                val episodePattern = Regex("E([0-9.]+)")
                val episodeMatch = episodePattern.find(episodeNumber)
                val season = seasonMatch?.groups?.get(1)?.value?.toInt() ?: 0
                val episode = episodeMatch?.groups?.get(1)?.value?.toInt() ?: 0

                val tmdbResponse = theMovieDbRepository.getRickAndMortyEpisodeDetails(season, episode)
                withContext(Dispatchers.Main) {
                    view.showEpisodeRatingAndImage(tmdbResponse.toTheMovieDbEpisode())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showEpisodeDetailsError()
                }
            }
        }
    }
}

interface CharacterDetailViewTranslator {
    fun showEpisode(episode: Episode)
    fun showEpisodeError()
    fun showNoInternetConnection()
    fun showEpisodeRatingAndImage(theMovieDbEpisode: TheMovieDbEpisode)
    fun showEpisodeDetailsError()
}