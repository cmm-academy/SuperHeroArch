package com.mstudio.superheroarch

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterLocalEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}