package com.example.sportz.domain.use_case

import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository

class SyncSportsDetailsUseCase(
    private val repository: SportsDetailsRepository
) {
    suspend fun insertSportsDetail(sportsDetails: SportsDetails) {
        repository.insertSportDetail(sportsDetails)
    }

    suspend fun getSportsFromLocal(id: Int): SportsDetails? {
        return repository.getSportByIdFromLocal(id)
    }
}