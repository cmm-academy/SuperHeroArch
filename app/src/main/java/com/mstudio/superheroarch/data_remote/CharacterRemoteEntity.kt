package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.repository.CharacterEntity

data class CharacterRemoteEntity(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val origin: UbicationRemoteEntity?,
    val location: UbicationRemoteEntity?,
    val episode: List<String>
)

fun CharacterRemoteEntity.mapToEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    originName = origin?.name.orEmpty(),
    locationName = location?.name.orEmpty(),
    firstEpisode = episode.first()
)

fun List<CharacterRemoteEntity>.mapToEntityList() = map { it.mapToEntity() }

data class UbicationRemoteEntity(
    val name: String,
)

data class CharacterResponse(
    val results: List<CharacterRemoteEntity>
)
