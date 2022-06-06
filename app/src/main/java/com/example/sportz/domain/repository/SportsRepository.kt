package com.example.sportz.domain.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    fun getSports(): Flow<Resource<List<Sports>>>
    suspend fun insertSports(sports: List<Sports>)
    suspend fun clearAllSports()
    suspend fun getSportsFromLocal(): List<Sports>
}