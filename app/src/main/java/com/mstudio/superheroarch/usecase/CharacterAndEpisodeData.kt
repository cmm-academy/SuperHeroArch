package com.mstudio.superheroarch.usecase

data class CharacterAndEpisodeData(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val episodeData: EpisodeData,
    val isFav: Boolean
)

data class EpisodeData(
    val episode: String,
    val name: String,
    val airDate: String,
    val image: String?,
    val voteAverage: Double?
)