package com.mstudio.superheroarch

import retrofit2.http.GET

interface RickMortyAPIService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}