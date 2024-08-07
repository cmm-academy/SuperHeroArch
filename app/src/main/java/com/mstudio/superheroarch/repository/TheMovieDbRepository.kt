package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.BuildConfig
import com.mstudio.superheroarch.remotedatasource.api.TheMovieDbApi
import com.mstudio.superheroarch.remotedatasource.model.TheMovieDbEpisodeRemoteEntity

class TheMovieDbRepository(
    private val api: TheMovieDbApi
) {

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    suspend fun getRickAndMortyEpisodeDetails(season: Int, episode: Int): TheMovieDbEpisodeRemoteEntity {
        val apiKey = BuildConfig.TMDB_API_KEY
        val response = api.getRickAndMortyEpisodeDetails("Bearer $apiKey", season, episode)
        if (response.isSuccessful) {
            response.body()?.let {
                return it.copy(image = "$BASE_IMAGE_URL${response.body()?.image}")
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

}