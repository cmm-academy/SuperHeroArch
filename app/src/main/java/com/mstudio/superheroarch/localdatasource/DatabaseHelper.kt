package com.mstudio.superheroarch.localdatasource

import androidx.room.Room
import com.mstudio.superheroarch.RickAndMortyApplication

object DatabaseHelper {

    fun create(): RickAndMortyDatabase = Room.databaseBuilder(
        RickAndMortyApplication.instance,
        RickAndMortyDatabase::class.java,
        "rickandmorty_database"
    ).build()

}