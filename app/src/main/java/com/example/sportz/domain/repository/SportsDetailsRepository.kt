package com.example.sportz.domain.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import kotlinx.coroutines.flow.Flow

interface SportsDetailsRepository {
    fun getSportById(id: Int): Flow<Resource<SportsDetails>>

    suspend fun getSportByIdFromLocal(id: Int): SportsDetails?

    suspend fun insertSportDetail(sportsDetails: SportsDetails)

    suspend fun deleteSportDetail(id: Int)
}