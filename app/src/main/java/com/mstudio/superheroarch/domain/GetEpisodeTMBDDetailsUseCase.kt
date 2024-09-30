package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.TmdbReposirory

class GetEpisodeTMDBDetailsUseCase(private val tmdbRepository: TmdbReposirory) {
    suspend operator fun invoke(seasonNumber: Int, episodeNumber: Int) =
        tmdbRepository.getEpisodeTMDBInfo(seasonNumber, episodeNumber)
}