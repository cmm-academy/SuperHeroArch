package com.mstudio.superheroarch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Query("SELECT * FROM episodes WHERE episode = :episodeCode")
    suspend fun getEpisodeByCode(episodeCode: String): Episode?
}
