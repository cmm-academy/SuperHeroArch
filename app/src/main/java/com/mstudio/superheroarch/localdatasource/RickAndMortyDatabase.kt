package com.mstudio.superheroarch.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity

@Database(entities = [CharacterLocalEntity::class], version = 2)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharactersDao
}