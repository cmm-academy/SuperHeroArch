package com.mstudio.superheroarch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class CharactersResponse(
    val info: Info,
    val results: List<Character>?
)

@Parcelize
data class Character(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    val origin: Ubication,
    val location: Ubication
) : Parcelable

@Parcelize
data class Ubication(
    val name: String,
    val url: String
) : Parcelable

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

