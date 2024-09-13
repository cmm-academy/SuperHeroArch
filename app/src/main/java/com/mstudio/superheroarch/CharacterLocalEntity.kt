package com.mstudio.superheroarch

import android.webkit.WebStorage.Origin
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("characters")
data class CharacterLocalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val firstEpisode: String
) : Serializable

fun CharacterLocalEntity.mapToEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    originName = originName,
    locationName = locationName,
    firstEpisode = firstEpisode
)

fun List<CharacterLocalEntity>.mapToEntityList() = map { it.mapToEntity() }

data class Episode(
    val air_date: String,
    val episode: String
) : Serializable
