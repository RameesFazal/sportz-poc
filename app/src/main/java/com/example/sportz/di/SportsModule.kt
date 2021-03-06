package com.example.sportz.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.sportz.common.Constants
import com.example.sportz.data.local.SportsDatabase
import com.example.sportz.data.remote.SportsApi
import com.example.sportz.data.repository.SportsDetailsRepositoryImpl
import com.example.sportz.data.repository.SportsRepositoryImpl
import com.example.sportz.domain.repository.SportsDetailsRepository
import com.example.sportz.domain.repository.SportsRepository
import com.example.sportz.domain.use_case.FetchSportsUseCase
import com.example.sportz.domain.use_case.FetchSportsItemUseCase
import com.example.sportz.domain.use_case.SyncSportsDetailsUseCase
import com.example.sportz.domain.use_case.SyncSportsListUseCase
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportsModule {

    @Provides
    @Singleton
    fun provideGetSportsUseCase(repository: SportsRepository): FetchSportsUseCase {
        return FetchSportsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSyncSportsListUseCase(repository: SportsRepository): SyncSportsListUseCase {
        return SyncSportsListUseCase(repository)
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
    fun provideGlideToVectorYou(@ApplicationContext context : Context): GlideToVectorYou {
        return GlideToVectorYou
            .init().with(context)
    }

    @Provides
    @Singleton
    fun provideGetSportsDetailsUseCase(repository: SportsDetailsRepository): FetchSportsItemUseCase {
        return FetchSportsItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSyncSportsItemUseCase(repository: SportsDetailsRepository): SyncSportsDetailsUseCase {
        return SyncSportsDetailsUseCase(repository)
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