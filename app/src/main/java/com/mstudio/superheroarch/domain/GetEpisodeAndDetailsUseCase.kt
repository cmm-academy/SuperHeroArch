package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TmdbRepository
import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity

class GetEpisodeAndDetailsUseCase(
    private val repository: RickAndMortyRepository,
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(episodeUrl: String): Result<Pair<EpisodeEntity, EpisodeTMDBInfoEntity>> {
        val episodeResult = repository.fetchEpisodeDetails(episodeUrl)
        if (episodeResult.isSuccess) {
            val episodeEntity = episodeResult.getOrNull()

            if (episodeEntity == null) {
                return Result.failure(Exception("Episode entity is null"))
            }

            val seasonAndEpisode = extractSeasonAndEpisode(episodeEntity.episode)
            val seasonNumber = seasonAndEpisode.first ?: 0
            val episodeNumber = seasonAndEpisode.second ?: 0

            val tmdbResult = tmdbRepository.getEpisodeTMDBInfo(seasonNumber, episodeNumber)

            if (tmdbResult == null) {
                return Result.failure(Exception("TMDB episode info is null"))
            }

            return Result.success(Pair(episodeEntity, tmdbResult))
        }
        return Result.failure(Exception("Failed to load episode details"))
    }

    private fun extractSeasonAndEpisode(episodeCode: String?): Pair<Int?, Int?> {
        return if (episodeCode != null && episodeCode.isNotEmpty()) {
            try {
                val season = episodeCode.substring(1, 3).toInt()
                val episode = episodeCode.substring(4, 6).toInt()
                Pair(season, episode)
            } catch (e: Exception) {
                Pair(null, null)
            }
        } else {
            Pair(null, null)
        }
    }
}
