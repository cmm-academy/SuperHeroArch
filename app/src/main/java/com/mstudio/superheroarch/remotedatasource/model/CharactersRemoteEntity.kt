package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class RickAndMortyRemoteEntity(
    @SerializedName("results") val characters: List<CharactersRemoteEntity>
)

data class CharactersRemoteEntity(
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("image") val image: String,
    @SerializedName("species") val species: String,
    @SerializedName("origin") val origin: CharacterPlaceRemoteEntity,
    @SerializedName("location") val location: CharacterPlaceRemoteEntity
) : Serializable

data class CharacterPlaceRemoteEntity(
    @SerializedName("name") val name: String
) : Serializable