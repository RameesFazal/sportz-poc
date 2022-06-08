package com.example.sportz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.example.sportz.data.local.SportsDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object SportsAppModuleTest {
    @Provides
    @Named("test_sports_db")
    fun provideSportsTestDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, SportsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}