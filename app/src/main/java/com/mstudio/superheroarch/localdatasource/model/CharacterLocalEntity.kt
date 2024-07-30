package com.mstudio.superheroarch.localdatasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mstudio.superheroarch.remotedatasource.model.CharacterPlaceRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity

@Entity
data class CharacterLocalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val episode: String
)

fun CharacterLocalEntity.toCharactersRemoteEntity(): CharactersRemoteEntity =
    CharactersRemoteEntity(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = CharacterPlaceRemoteEntity(origin),
        location = CharacterPlaceRemoteEntity(location),
        episode = episode.split(",")
    )