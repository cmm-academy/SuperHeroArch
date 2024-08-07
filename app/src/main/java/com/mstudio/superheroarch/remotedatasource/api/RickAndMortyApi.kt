package com.mstudio.superheroarch.remotedatasource.api

import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {

    companion object {
        const val GET_CHARACTERS = "character"
    }

    @GET(GET_CHARACTERS)
    suspend fun getCharacters(): Response<RickAndMortyRemoteEntity>

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: Int): Response<EpisodeRemoteEntity>
}

object RickAndMortyApiHelper {
    fun create(): RickAndMortyApi = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java)
}