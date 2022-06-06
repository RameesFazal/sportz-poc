package com.example.sportz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportz.data.local.entity.SportsEntity

@Dao
interface SportsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSports(sports: List<SportsEntity>)

    @Query("DELETE FROM sportsentity")
    suspend fun clearAllEntities()

    @Query("SELECT * FROM sportsentity")
    suspend fun getAllSports() : List<SportsEntity>
}