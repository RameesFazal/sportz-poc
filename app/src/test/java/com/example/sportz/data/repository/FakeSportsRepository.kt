package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSportsRepository : SportsRepository {
    private val sportsList = mutableListOf<Sports>()

    override fun getSports(): Flow<Resource<List<Sports>>> {
        return flow {
            emit(Resource.Success(sportsList.toList()))
        }
    }

    override suspend fun insertSports(sports: List<Sports>) {
        sportsList.addAll(sports)
    }

    override suspend fun clearAllSports() {
        sportsList.clear()
    }

    override suspend fun getSportsFromLocal(): List<Sports> {
        return sportsList
    }
}