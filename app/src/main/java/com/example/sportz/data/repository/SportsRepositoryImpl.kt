package com.example.sportz.data.repository

import com.example.sportz.common.Resource
import com.example.sportz.data.local.SportsDao
import com.example.sportz.data.remote.SportsApi
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SportsRepositoryImpl(
    private val api: SportsApi,
    private val dao: SportsDao
) : SportsRepository {
    override fun getSports(): Flow<Resource<List<Sports>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteSports = api.getSports().data.filter {it.attributes.name != null && it.attributes.icon != null }.map { it.toSports() }
            emit(Resource.Success(remoteSports))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, Please check your connection!",
                    data = listOf()
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Oops! Something went wrong",
                    data = listOf()
                )
            )
        }
    }

    override suspend fun insertSports(sports: List<Sports>) {
        dao.insertSports(sports.map { it.toSportsEntity() })
    }

    override suspend fun clearAllSports() {
        dao.clearAllEntities()
    }

    override suspend fun getSportsFromLocal(): List<Sports> {
        return dao.getAllSports().map { it.toSports() }
    }
}