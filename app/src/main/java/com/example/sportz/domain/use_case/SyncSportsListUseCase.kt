package com.example.sportz.domain.use_case

import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository

class SyncSportsListUseCase(
    private val repository: SportsRepository
) {
    suspend fun insertSports(sports: List<Sports>) {
        repository.insertSports(sports)
    }

    suspend fun clearAllSports() {
        repository.clearAllSports()
    }

    suspend fun getSportsFromLocal() : List<Sports>{
        return repository.getSportsFromLocal()
    }
}