package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository

class GetEpisodeDetailsUseCase(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(episodeUrl: String): Result<EpisodeEntity> {
        return repository.fetchEpisodeDetails(episodeUrl)
    }
}
