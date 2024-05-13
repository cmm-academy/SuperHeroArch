package com.mstudio.superheroarch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterLocalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val episodes: String
)

fun CharacterLocalEntity.toCharacter() = Character(
    id = id,
    name = name,
    status = status,
    image = image,
    species = species,
    gender = gender,
    origin = Location(name = origin),
    location = Location(name = location),
    episodes = episodes.split(", ")
)

fun List<CharacterLocalEntity>.toCharacterList() = map { it.toCharacter() }
