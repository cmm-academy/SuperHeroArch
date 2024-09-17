package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getEpisode(episodeUrl: String): EpisodeEntity
}