package com.mstudio.superheroarch.localdatasource

import androidx.room.Room
import com.mstudio.superheroarch.RickAndMortyApplication

object DataBaseInstance {

    val database = Room.databaseBuilder(
        RickAndMortyApplication.instance,
        RickAndMortyDatabase::class.java,
        "rickandmorty_database"
    ).build()
}