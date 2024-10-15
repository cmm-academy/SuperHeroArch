package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.BuildConfig

class TmdbRemoteDataSourceImpl (private val tmdbApi: TmdbApi) {

    suspend fun getEpisodeDetails(seasonNumber: Int, episodeNumber: Int): EpisodeTMDBInfoRemoteEntity? {
        val response = tmdbApi.getEpisodeDetails(seasonNumber, episodeNumber, "Bearer ${BuildConfig.TMDB_API_KEY}")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}