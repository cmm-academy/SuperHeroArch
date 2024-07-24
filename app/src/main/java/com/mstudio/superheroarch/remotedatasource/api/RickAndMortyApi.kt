package com.mstudio.superheroarch.remotedatasource.api

import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    companion object {
        const val GET_CHARACTERS = "character?"
    }

    @GET(GET_CHARACTERS)
    suspend fun getCharacters(@Query("status") status: String): Response<RickAndMortyRemoteEntity>
}