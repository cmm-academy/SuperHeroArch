package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.BuildConfig
import com.mstudio.superheroarch.remotedatasource.api.TheMovieDbApi
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEntity
import com.mstudio.superheroarch.repository.model.TheMovieDbEpisodeEntity

class TheMovieDbRepository(
    private val api: TheMovieDbApi
) {

    suspend fun getRickAndMortyEpisodeDetails(season: Int, episode: Int): TheMovieDbEpisodeEntity {
        val apiKey = BuildConfig.TMDB_API_KEY
        val response = api.getRickAndMortyEpisodeDetails("Bearer $apiKey", season, episode)
        if (response.isSuccessful) {
            response.body()?.let {
                return it.toTheMovieDbEntity()
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

}