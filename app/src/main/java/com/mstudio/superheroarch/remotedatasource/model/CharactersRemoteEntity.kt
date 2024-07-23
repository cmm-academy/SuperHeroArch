package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName

data class RickAndMortyRemoteEntity(
    @SerializedName("results") val characters: List<CharactersRemoteEntity>
)

data class CharactersRemoteEntity(
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("image") val image: String
)