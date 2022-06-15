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
        try {
            val remoteSports = api.getSportsDetail(id)
            emit(Resource.Success(remoteSports.data.toSportsDetails()))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, Please check your connection!",
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Oops! Something went wrong",
                )
            )
        }
    }

    override suspend fun getSportByIdFromLocal(id: Int): SportsDetails? {
        val res = sportsDetailsDao.getSportById(id)
        res?.let {
            return it.toSportsDetails()
        }?: run {
            return null
        }
    }

    override suspend fun insertSportDetail(sportsDetails: SportsDetails) {
        sportsDetailsDao.insertSport(sportsDetails.toSportsDetailsEntity())
    }
}