package com.mstudio.superheroarch.repository.model

import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.presentation.model.CharacterData

data class CharacterEntity(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val episode: List<String>,
    val isFav: Boolean = false
)

fun CharacterEntity.toCharacterData(): CharacterData =
    CharacterData(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin,
        location = location,
        firstEpisode = episode.first().split("/").last(),
        isFav = isFav
    )

fun CharacterEntity.toCharacterLocalEntity(): CharacterLocalEntity =
    CharacterLocalEntity(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin,
        location = location,
        episode = episode.joinToString(),
        isFav = isFav
    )