package com.mstudio.superheroarch.data

data class CharactersResponse(
    val info: Info,
    val results: List<Result>?
)

data class Result(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

