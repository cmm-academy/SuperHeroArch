package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.data_local.CharacterLocalEntity
import java.io.Serializable

data class CharacterEntity(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val firstEpisode: String
):Serializable

fun CharacterEntity.mapToLocalEntity() = CharacterLocalEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    originName = originName,
    locationName = locationName,
    firstEpisode = firstEpisode
)

fun List<CharacterEntity>.mapToLocalEntityList() = map { it.mapToLocalEntity() }