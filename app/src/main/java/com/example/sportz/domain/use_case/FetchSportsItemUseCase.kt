package com.example.sportz.domain.use_case

import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository
import kotlinx.coroutines.flow.Flow

class FetchSportsItemUseCase(
    private val repository: SportsDetailsRepository
) {
    operator fun invoke(id: Int): Flow<Resource<SportsDetails>> {
        return repository.getSportById(id)
    }
}