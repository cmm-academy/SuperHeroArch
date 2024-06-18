package com.mstudio.superheroarch.network

import com.mstudio.superheroarch.data.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET


interface RickAndMortyApi {
    @GET("character")
    fun doGetCharacters(): Call<CharactersResponse>
}
