package com.example.sportz.di

import android.app.Application
import androidx.room.Room
import com.example.sportz.common.Constants
import com.example.sportz.data.local.SportsDatabase
import com.example.sportz.data.remote.SportsApi
import com.example.sportz.data.repository.SportsDetailsRepositoryImpl
import com.example.sportz.data.repository.SportsRepositoryImpl
import com.example.sportz.domain.repository.SportsDetailsRepository
import com.example.sportz.domain.repository.SportsRepository
import com.example.sportz.domain.use_case.GetSports
import com.example.sportz.domain.use_case.GetSportsDetail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportsModule {

    @Provides
    @Singleton
    fun provideGetSportsUseCase(repository: SportsRepository): GetSports {
        return GetSports(repository)
    }

    @Provides
    @Singleton
    fun providesSportsRepository(
        db: SportsDatabase,
        api: SportsApi
    ): SportsRepository {
        return SportsRepositoryImpl(dao = db.dao, api = api)
    }

    @Provides
    @Singleton
    fun provideGetSportsDetailsUseCase(repository: SportsDetailsRepository): GetSportsDetail {
        return GetSportsDetail(repository)
    }

    @Provides
    @Singleton
    fun providesSportsDetailsRepository(
        db: SportsDatabase,
        api: SportsApi
    ): SportsDetailsRepository {
        return SportsDetailsRepositoryImpl(sportsDetailsDao = db.detailsDao, api = api)
    }

    @Provides
    @Singleton
    fun providesSportsDatabase(app: Application): SportsDatabase {
        return Room.databaseBuilder(app, SportsDatabase::class.java, Constants.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesSportsApi(): SportsApi {
        return Retrofit.Builder().baseUrl(Constants.SPORTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SportsApi::class.java)
    }
}