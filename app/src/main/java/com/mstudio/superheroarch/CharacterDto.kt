package com.mstudio.superheroarch

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val origin: UbicationDto?,
    val location: UbicationDto?,
    val episode: List<String>
)

data class UbicationDto(
    val name: String,
    val url: String
)

data class EpisodeDto(
    val air_date: String,
    val episode: String
)

data class CharacterResponse(
    val results: List<CharacterDto>
)
