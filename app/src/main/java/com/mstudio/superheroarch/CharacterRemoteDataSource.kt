package com.mstudio.superheroarch

import retrofit2.Response

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getEpisode(episodeUrl: String): Response<EpisodeEntity>
}