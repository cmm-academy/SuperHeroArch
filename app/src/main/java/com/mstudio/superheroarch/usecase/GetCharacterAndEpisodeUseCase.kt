package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.model.toCharacterAndEpisode
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TheMovieDbRepository
import com.mstudio.superheroarch.repository.model.TheMovieDbEpisodeEntity
import com.mstudio.superheroarch.repository.model.toEpisode
import com.mstudio.superheroarch.repository.model.toTheMovieDbEpisode

class GetCharacterAndEpisodeUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val theMovieDbRepository: TheMovieDbRepository
) {

    companion object {
        private const val SEASON_REGEX_PATTERN = "S([0-9.]+)"
        private const val EPISODE_REGEX_PATTERN = "E([0-9.]+)"
    }

    suspend fun getCharacterAndEpisode(characterData: CharacterData): CharacterAndEpisodeData? {
        try {
            val episodeDetails = rickAndMortyRepository.getSingleEpisode(characterData.firstEpisode.toInt())
            val response = getEpisodeScoreAndImage(episodeDetails.episode)
            return characterData.toCharacterAndEpisode(episodeDetails.toEpisode(), response?.toTheMovieDbEpisode())
        } catch (e: Exception) {
            return null
        }
    }

    private suspend fun getEpisodeScoreAndImage(episodeNumber: String): TheMovieDbEpisodeEntity? {
        try {
            val seasonPattern = Regex(SEASON_REGEX_PATTERN)
            val seasonMatch = seasonPattern.find(episodeNumber)
            val episodePattern = Regex(EPISODE_REGEX_PATTERN)
            val episodeMatch = episodePattern.find(episodeNumber)
            val season = seasonMatch?.groups?.get(1)?.value?.toInt() ?: 0
            val episode = episodeMatch?.groups?.get(1)?.value?.toInt() ?: 0

            val response = theMovieDbRepository.getRickAndMortyEpisodeDetails(season, episode)
            return response
        } catch (e: Exception) {
            return null
        }
    }
}