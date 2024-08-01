package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.model.toCharacterAndEpisode
import com.mstudio.superheroarch.remotedatasource.model.toEpisode
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEpisode
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TheMovieDbRepository

class GetCharacterAndEpisodeUseCase {

    companion object {
        private const val SEASON_REGEX_PATTERN = "S([0-9.]+)"
        private const val EPISODE_REGEX_PATTERN = "E([0-9.]+)"
    }

    val rickAndMortyRepository = RickAndMortyRepository()
    val theMovieDbRepository = TheMovieDbRepository()

    suspend fun getCharacterAndEpisode(characterData: CharacterData): CharacterAndEpisodeData? {
        try {
            val episodeDetails = rickAndMortyRepository.getSingleEpisode(characterData.firstEpisode.toInt())
            val seasonPattern = Regex(SEASON_REGEX_PATTERN)
            val seasonMatch = seasonPattern.find(episodeDetails.episode)
            val episodePattern = Regex(EPISODE_REGEX_PATTERN)
            val episodeMatch = episodePattern.find(episodeDetails.episode)
            val season = seasonMatch?.groups?.get(1)?.value?.toInt() ?: 0
            val episode = episodeMatch?.groups?.get(1)?.value?.toInt() ?: 0

            val response = theMovieDbRepository.getRickAndMortyEpisodeDetails(season, episode)
            return characterData.toCharacterAndEpisode(episodeDetails.toEpisode(), response.toTheMovieDbEpisode())
        } catch (e: Exception) {
            return null
        }
    }
}