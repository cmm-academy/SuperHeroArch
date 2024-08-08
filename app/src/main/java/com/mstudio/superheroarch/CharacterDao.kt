package com.mstudio.superheroarch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg characters: Character)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<Character>
}
