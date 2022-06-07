package com.example.sportz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportz.data.local.entity.SportDetailsEntity
import com.example.sportz.data.local.entity.SportsEntity

@Database(
    entities = [
        SportsEntity::class,
        SportDetailsEntity::class
    ],
    version = 1
)
abstract class SportsDatabase : RoomDatabase() {
    abstract val dao: SportsDao
    abstract val detailsDao: SportsDetailsDao
}