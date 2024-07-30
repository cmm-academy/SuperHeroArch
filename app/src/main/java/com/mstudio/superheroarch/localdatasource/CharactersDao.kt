package com.mstudio.superheroarch.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM CharacterLocalEntity")
    fun getCharacters(): List<CharacterLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterLocalEntity>)
}