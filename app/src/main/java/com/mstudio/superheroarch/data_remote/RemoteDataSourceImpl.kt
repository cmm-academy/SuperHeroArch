package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.CharactersFetchException
import com.mstudio.superheroarch.EpisodeFetchException

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