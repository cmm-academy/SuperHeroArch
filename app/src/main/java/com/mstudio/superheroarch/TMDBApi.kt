package com.mstudio.superheroarch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TMDBApi {
    @GET("season/{season}/episode/{episode}")
    suspend fun getEpisodeDetails(@Header ("Authorization") token: String, @Path("season") season: Int, @Path("episode") episode: Int): Response<TMDBEpisodeData>
}