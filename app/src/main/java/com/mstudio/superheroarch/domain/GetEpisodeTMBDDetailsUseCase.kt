package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.TmdbRepository

class GetEpisodeTMDBDetailsUseCase(private val tmdbRepository: TmdbRepository) {
    suspend operator fun invoke(seasonNumber: Int, episodeNumber: Int) =
        tmdbRepository.getEpisodeTMDBInfo(seasonNumber, episodeNumber)
}