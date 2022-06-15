package com.example.sportz.domain.use_case

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow

class FetchSportsUseCase(
    private val repository: SportsRepository
) {
    operator fun invoke() : Flow<Resource<List<Sports>>> {
        return repository.getSports()
    }
}