package com.mstudio.superheroarch.remotedatasource.api

import com.mstudio.superheroarch.remotedatasource.model.TheMovieDbEpisodeRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TheMovieDbApi {

    @GET("season/{season}/episode/{episode}")
    suspend fun getRickAndMortyEpisodeDetails(
        @Header("Authorization") apiKey: String,
        @Path("season") season: Int,
        @Path("episode") episode: Int
    ): Response<TheMovieDbEpisodeRemoteEntity>
}