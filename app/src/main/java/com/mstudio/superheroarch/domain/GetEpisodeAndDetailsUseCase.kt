package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.presentation.EpisodeDetailsViewEntity
import com.mstudio.superheroarch.presentation.SeasonAndEpisode
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TmdbRepository

class GetEpisodeAndDetailsUseCase(
    private val repository: RickAndMortyRepository,
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(episodeUrl: String): Result<EpisodeDetailsViewEntity> {
        return try {
            val episodeEntity = repository.fetchEpisodeDetails(episodeUrl)

            val seasonAndEpisode = extractSeasonAndEpisode(episodeEntity.episode)
            if (seasonAndEpisode != null) {
                val seasonNumber = seasonAndEpisode.seasonNumber
                val episodeNumber = seasonAndEpisode.episodeNumber

                val tmdbResult = tmdbRepository.getEpisodeTMDBInfo(seasonNumber, episodeNumber)

                if (tmdbResult != null) {
                    val episodeDetailsViewEntity = EpisodeDetailsViewEntity(
                        air_date = episodeEntity.air_date,
                        episode = episodeEntity.episode,
                        rating = tmdbResult.rating,
                        imageUrl = "https://image.tmdb.org/t/p/w500${tmdbResult.imageUrl}"
                    )
                    Result.success(episodeDetailsViewEntity)
                } else {
                    Result.failure(Exception("TMDB episode info is null"))
                }
            } else {
                Result.failure(Exception("Season and episode not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private fun extractSeasonAndEpisode(episodeCode: String?): SeasonAndEpisode? {
        if (episodeCode.isNullOrEmpty()) {
            return null
        }

        val regex = Regex("S(\\d{2})E(\\d{2})")
        val matchResult = regex.find(episodeCode)

        return if (matchResult != null) {
            val (season, episode) = matchResult.destructured
            SeasonAndEpisode(season.toInt(), episode.toInt())
        } else {
            null
        }
    }


}