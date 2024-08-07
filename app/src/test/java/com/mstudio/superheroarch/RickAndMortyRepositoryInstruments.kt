package com.mstudio.superheroarch

import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.remotedatasource.model.CharacterPlaceRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.TheMovieDbEpisodeRemoteEntity
import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.EpisodeData

object RickAndMortyRepositoryInstruments {

    fun givenACharacterRemoteEntity(): CharactersRemoteEntity =
        CharactersRemoteEntity(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            origin = CharacterPlaceRemoteEntity("Earth"),
            location = CharacterPlaceRemoteEntity("City"),
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )

    fun givenACharacterLocalEntity(): CharacterLocalEntity =
        CharacterLocalEntity(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            origin = "Earth",
            location = "City",
            episode = "https://rickandmortyapi.com/api/episode/1",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )

    fun givenAnEpisodeRemoteEntity(): EpisodeRemoteEntity =
        EpisodeRemoteEntity(
            name = "episode name",
            airDate = "October 16, 2014",
            episode = "S01E01"
        )

    fun givenAnEpisodeExtraData(): TheMovieDbEpisodeRemoteEntity =
        TheMovieDbEpisodeRemoteEntity(
            image = "image",
            voteAverage = 7.0
        )

    fun givenCharacterCompleteDetailData(episodeImage: String? = "image", voteAverage: Double? = 7.0): CharacterAndEpisodeData =
        CharacterAndEpisodeData(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            origin = "Earth",
            location = "City",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodeData = EpisodeData(
                episode = "S01E01",
                name = "episode name",
                airDate = "October 16, 2014",
                image = episodeImage,
                voteAverage = voteAverage
            )
        )

    fun givenCharacterData(): CharacterData =
        CharacterData(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            origin = "Earth",
            location = "City",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            firstEpisode = "S01E01"
        )
}