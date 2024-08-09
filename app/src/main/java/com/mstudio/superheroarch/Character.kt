package com.mstudio.superheroarch

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("characters")
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val firstEpisode: String
) : Serializable {
    constructor(
        id: Int,
        name: String,
        status: String,
        image: String,
        origin: Ubication,
        location: Ubication,
        episode: List<String>
    ) : this(
        id,
        name,
        status,
        image,
        origin.name,
        location.name,
        episode.firstOrNull() ?: ""
    )
}

data class Ubication(
    val name: String,
    val url: String
) : Serializable

data class Episode(
    val air_date: String,
    val episode: String
) : Serializable
