package com.mstudio.superheroarch.localdatasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mstudio.superheroarch.repository.model.CharacterEntity

@Entity
data class CharacterLocalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val episode: String,
    val isFavorite: Boolean
)

fun CharacterLocalEntity.toCharactersEntity(): CharacterEntity =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin,
        location = location,
        episode = episode.split(","),
        isFavorite = isFavorite
    )