package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockSportsRepository : SportsRepository {

    private val sportsList = mutableListOf<Sports>()
    var shouldReturnNetworkError: Boolean = false

    override fun getSports(): Flow<Resource<List<Sports>>> {
        flow {
            emit(Resource.Loading(null))
        }
        if (shouldReturnNetworkError) {
            return flow {
                emit(Resource.Error("Oops! Something went wrong", sportsList.toList()))
            }
        }
        sportsList.clear()
        val newSports = Sports(id = 27, name = "Boxing", icon = "")
        sportsList.add(newSports)
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