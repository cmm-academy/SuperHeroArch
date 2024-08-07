package com.mstudio.superheroarch.presentation.model

import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.EpisodeData
import java.io.Serializable

data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val firstEpisode: String
) : Serializable

fun CharacterData.toCharacterAndEpisode(episode: Episode, theMovieDbEpisode: TheMovieDbEpisode?): CharacterAndEpisodeData =
    CharacterAndEpisodeData(
        id = id,
        name = name,
        status = status,
        image = image,
        species = species,
        origin = origin,
        location = location,
        episodeData = EpisodeData(
            episode = episode.episode,
            name = episode.name,
            airDate = episode.airDate,
            image = theMovieDbEpisode?.image,
            voteAverage = theMovieDbEpisode?.voteAverage
        )
    )