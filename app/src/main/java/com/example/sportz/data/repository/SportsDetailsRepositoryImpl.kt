package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.data.local.SportsDetailsDao
import com.example.sportz.data.remote.SportsApi
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SportsDetailsRepositoryImpl(
    private val api: SportsApi,
    private val sportsDetailsDao: SportsDetailsDao
) : SportsDetailsRepository {

    override fun getSportById(id: Int): Flow<Resource<SportsDetails>> = flow {
        emit(Resource.Loading())
        val sportsDetails = getSportByIdFromLocal(id)
        sportsDetails?.let {
            emit(Resource.Success(sportsDetails))
        }

        try {
            val remoteSports = api.getSportsDetail(id)
            insertSportDetail(remoteSports.data.toSportsDetails())
            val newDetails = getSportByIdFromLocal(id)
            newDetails?.let {
                emit(Resource.Success(newDetails))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, Please check your connection!",
                    data = sportsDetails
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Oops! Something went wrong",
                    data = sportsDetails
                )
            )
        }
    }

    override suspend fun getSportByIdFromLocal(id: Int): SportsDetails? {
        return sportsDetailsDao.getSportById(id)?.toSportsDetails()
    }

    override suspend fun insertSportDetail(sportsDetails: SportsDetails) {
        sportsDetailsDao.insertSport(sportsDetails.toSportsDetailsEntity())
    }
}