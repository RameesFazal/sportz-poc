package com.example.sportz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportz.data.local.entity.SportDetailsEntity

@Dao
interface SportsDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSport(sport: SportDetailsEntity)

    @Query("SELECT * FROM sportdetailsentity WHERE sportsId = :id")
    suspend fun getSportById(id: Int): SportDetailsEntity?
}