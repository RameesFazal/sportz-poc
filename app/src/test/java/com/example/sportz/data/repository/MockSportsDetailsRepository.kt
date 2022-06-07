package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockSportsDetailsRepository : SportsDetailsRepository {

    private val sportsList = mutableListOf<SportsDetails>()

    override fun getSportById(id: Int): Flow<Resource<SportsDetails>> {
        return flow {
            val sportDetails: SportsDetails = sportsList.first { id == id }
            emit(Resource.Success(sportDetails))
        }
    }

    override suspend fun getSportByIdFromLocal(id: Int): SportsDetails? {
        return sportsList.first { id == id }
    }

    override suspend fun insertSportDetail(sportsDetails: SportsDetails) {
        sportsList.add(sportsDetails)
    }

    override suspend fun deleteSportDetail(id: Int) {
        sportsList.removeAll { it.id == id }
    }
}