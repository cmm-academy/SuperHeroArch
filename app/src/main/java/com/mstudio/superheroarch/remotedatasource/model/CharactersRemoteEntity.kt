package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.presentation.model.CharacterData
import java.io.Serializable


data class RickAndMortyRemoteEntity(
    @SerializedName("results") val characters: List<CharactersRemoteEntity>
)

data class CharactersRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("image") val image: String,
    @SerializedName("species") val species: String,
    @SerializedName("origin") val origin: CharacterPlaceRemoteEntity,
    @SerializedName("location") val location: CharacterPlaceRemoteEntity,
    @SerializedName("episode") val episode: List<String>
) : Serializable

data class CharacterPlaceRemoteEntity(
    @SerializedName("name") val name: String
) : Serializable

fun CharactersRemoteEntity.toCharacterLocalEntity(): CharacterLocalEntity =
    CharacterLocalEntity(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin.name,
        location = location.name,
        episode = episode.joinToString()
    )

fun CharactersRemoteEntity.toCharacterData(): CharacterData =
    CharacterData(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin.name,
        location = location.name,
        firstEpisode = episode.first().split("/").last()
    )