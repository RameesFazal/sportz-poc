package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockSportsDetailsRepository : SportsDetailsRepository {

    private val sportsList = mutableListOf<SportsDetails>()
    var shouldReturnNetworkError: Boolean = false

    override fun getSportById(id: Int): Flow<Resource<SportsDetails>> {
        if (shouldReturnNetworkError) {
            return flow {
                emit(Resource.Error("Oops! Something went wrong"))
            }
        }
        return flow {
            val sportDetails = SportsDetails(
                id = 1,
                name = "Baseball",
                description = "A Sample Description",
                image = ""
            )
            emit(Resource.Success(sportDetails))
        }
    }

    override suspend fun getSportByIdFromLocal(id: Int): SportsDetails? {
        val res = sportsList.filter { id == id }
        if (res.isNotEmpty()) {
            return res.first()
        }
        return null
    }

    override suspend fun insertSportDetail(sportsDetails: SportsDetails) {
        sportsList.add(sportsDetails)
    }
}