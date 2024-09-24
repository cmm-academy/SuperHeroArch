package com.mstudio.superheroarch.data_remote

class TmdbRemoteDataSourceImpl (private val tmdbApi: TmdbApi) {

    suspend fun getEpisodeDetails(seasonNumber: Int, episodeNumber: Int): EpisodeTMDBInfoRemoteEntity? {
        val response = tmdbApi.getEpisodeDetails(seasonNumber, episodeNumber)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}