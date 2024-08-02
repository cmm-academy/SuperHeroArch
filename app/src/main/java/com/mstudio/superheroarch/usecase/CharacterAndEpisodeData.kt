package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.TheMovieDbEpisode

data class CharacterAndEpisodeData(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val firsEpisode: FirstEpisodeData
)

data class FirstEpisodeData(
    val episode: String,
    val name: String,
    val airDate: String,
    val episodeNumber: String,
    val episodeExtraData: TheMovieDbEpisode?
)