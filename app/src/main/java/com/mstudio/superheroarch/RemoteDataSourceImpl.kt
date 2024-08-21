package com.mstudio.superheroarch

import retrofit2.Response

class RemoteDataSourceImpl(private val apiRick: ApiRick) : CharacterRemoteDataSource {
    override suspend fun getCharacters(): Response<CharacterResponse> {
        return apiRick.getCharacter()
    }

    override suspend fun getEpisode(episodeUrl: String): Response<Episode> {
        return apiRick.getEpisode(episodeUrl)
    }
}