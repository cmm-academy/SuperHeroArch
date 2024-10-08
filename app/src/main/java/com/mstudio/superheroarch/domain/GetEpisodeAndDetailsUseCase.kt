package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.presentation.EpisodeDetailsViewEntity
import com.mstudio.superheroarch.presentation.SeasonAndEpisode
import com.mstudio.superheroarch.presentation.mapToEpisodeDetailsViewEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TmdbRepository

class GetEpisodeAndDetailsUseCase(
    private val repository: RickAndMortyRepository,
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(episodeUrl: String): Result<EpisodeDetailsViewEntity> {
        val episodeResult = repository.fetchEpisodeDetails(episodeUrl)
        if (episodeResult.isSuccess) {
            val episodeEntity = episodeResult.getOrThrow()

            val seasonAndEpisode = extractSeasonAndEpisode(episodeEntity.episode)
            val seasonNumber = seasonAndEpisode.seasonNumber ?: 0
            val episodeNumber = seasonAndEpisode.episodeNumber ?: 0

            val tmdbResult = tmdbRepository.getEpisodeTMDBInfo(seasonNumber, episodeNumber)

            if (tmdbResult != null) {
                val episodeDetailsViewEntity = mapToEpisodeDetailsViewEntity(
                    air_date = episodeEntity.air_date,
                    episode = episodeEntity.episode,
                    rating = tmdbResult.rating,
                    imageUrl = "https://image.tmdb.org/t/p/w500${tmdbResult.imageUrl}"
                )
                return Result.success(episodeDetailsViewEntity)
            } else {
                return Result.failure(Exception("TMDB episode info is null"))
            }
        }
        return Result.failure(Exception("Failed to load episode details"))
    }

    private fun extractSeasonAndEpisode(episodeCode: String?): SeasonAndEpisode {
        return if (episodeCode != null && episodeCode.isNotEmpty()) {
            try {
                val season = episodeCode.substring(1, 3).toInt()
                val episode = episodeCode.substring(4, 6).toInt()
                SeasonAndEpisode(season, episode)
            } catch (e: Exception) {
                SeasonAndEpisode(null, null)
            }
        } else {
            SeasonAndEpisode(null, null)
        }
    }
}