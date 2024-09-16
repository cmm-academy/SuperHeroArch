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
        throw CharactersFetchException("Response: $response")
    }

    override suspend fun getEpisode(episodeUrl: String): EpisodeEntity {
        val response = apiRick.getEpisode(episodeUrl)

        if (response.isSuccessful){
            val responseBody = response.body()
            val episodeEntity = responseBody?.mapToEntity()

            if (episodeEntity != null){
                return episodeEntity
            }
        }
        throw EpisodeFetchException(episodeUrl, "Response: $response")
    }
}