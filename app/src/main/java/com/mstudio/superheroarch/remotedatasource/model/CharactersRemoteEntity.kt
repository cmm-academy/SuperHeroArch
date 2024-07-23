package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.model.CharacterModel

data class RickAndMortyRemoteEntity(
    @SerializedName("results") val characters: List<CharactersRemoteEntity>
)

data class CharactersRemoteEntity(
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("image") val image: String
)

fun CharactersRemoteEntity.toCharacter(): CharacterModel =
    CharacterModel(
        name = name,
        status = status,
        image = image
    )