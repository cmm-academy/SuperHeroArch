package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.CharacterEntity

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val firstEpisode: String
)

fun CharacterEntity.toDomainModel() = Character(
    id = id,
    name = name,
    status = status,
    image = image,
    originName = originName,
    locationName = locationName,
    firstEpisode = firstEpisode
)

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        image = this.image,
        originName = this.originName,
        locationName = this.locationName,
        firstEpisode = this.firstEpisode
    )
}