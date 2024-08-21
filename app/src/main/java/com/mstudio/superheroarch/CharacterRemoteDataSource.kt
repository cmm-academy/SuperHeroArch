package com.mstudio.superheroarch

import retrofit2.Response

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): Response<CharacterResponse>
    suspend fun getEpisode(episodeUrl: String): Response<Episode>
}