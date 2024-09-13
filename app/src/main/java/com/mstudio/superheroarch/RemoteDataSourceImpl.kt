package com.mstudio.superheroarch

import retrofit2.Response

class RemoteDataSourceImpl(private val apiRick: ApiRick) : CharacterRemoteDataSource {
    override suspend fun getCharacters(): List<CharacterEntity> {
        val response = apiRick.getCharacters()

        if (response.isSuccessful) {
            val responseBody = response.body()

            if (responseBody != null) {
                return responseBody.results.mapToEntityList()
            }
        }
        throw Exception("")
    }

    override suspend fun getEpisode(episodeUrl: String): Response<EpisodeEntity> {
        return apiRick.getEpisode(episodeUrl)
    }
}