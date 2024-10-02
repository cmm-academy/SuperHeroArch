package com.mstudio.superheroarch.domain
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TmdbRepository

sealed class DataRequest {
    object Characters : DataRequest()
    data class EpisodeDetails(val episodeUrl: String) : DataRequest()
    data class TmdbEpisodeDetails(val seasonNumber: Int, val episodeNumber: Int) : DataRequest()
}

class FetchDataUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(request: DataRequest): Result<Any> {
        return try {
            when (request) {
                is DataRequest.Characters -> {
                    val characters = rickAndMortyRepository.fetchCharacters()
                    characters.map { it }
                }
                is DataRequest.EpisodeDetails -> {
                    val episodeDetails = rickAndMortyRepository.fetchEpisodeDetails(request.episodeUrl)
                    episodeDetails.map { it }
                }
                is DataRequest.TmdbEpisodeDetails -> {
                    val tmdbDetails = tmdbRepository.getEpisodeTMDBInfo(request.seasonNumber, request.episodeNumber)
                    if (tmdbDetails != null) {
                        Result.success(tmdbDetails)
                    } else {
                        Result.failure(Throwable("No TMDB details found for the given episode"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
