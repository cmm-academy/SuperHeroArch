package com.mstudio.superheroarch

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val episodes: List<String>
) : Serializable

data class Location(
    val name: String
) : Serializable

fun Character.toCharacterLocalEntity() = CharacterLocalEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    species = species,
    gender = gender,
    origin = origin.name,
    location = location.name,
    episodes = episodes.joinToString(", ")
)

fun List<Character>.toCharacterLocalEntityList() = map { it.toCharacterLocalEntity() }

fun Character.toCharacterUIEntity(episode: Episode, tmdbEpisodeData: TMDBEpisodeData) = FullCharacterEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    species = species,
    gender = gender,
    origin = origin.name,
    location = location.name,
    firstEpisode = EpisodeUIEntity(
        name = episode.name,
        releaseDate = episode.releaseDate,
        episodeNumber = episode.episodeNumber,
        rating = tmdbEpisodeData.rating,
        imagePath = tmdbEpisodeData.imagePath
    )
)