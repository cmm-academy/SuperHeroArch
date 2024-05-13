package com.mstudio.superheroarch

data class CharacterUIEntity(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val firstEpisode: EpisodeUIEntity
)

data class EpisodeUIEntity(
    val name: String,
    val releaseDate: String,
    val episodeNumber: String,
    val rating: Double,
    val imagePath: String
)
