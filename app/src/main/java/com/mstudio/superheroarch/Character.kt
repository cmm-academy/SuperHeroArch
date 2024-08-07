package com.mstudio.superheroarch

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "characters")
@TypeConverters(StringListConverter::class)
data class Character(
    @PrimaryKey val name: String,
    val status: String,
    val image: String,
    val origin: Ubication,
    val location: Ubication,
    val episode: List<String>
) : Serializable

data class Ubication(
    val name: String,
    val url: String
) : Serializable

data class Episode(
    val air_date: String,
    val episode: String,
) : Serializable

data class CharacterResponse(
    val results: List<Character>
)
