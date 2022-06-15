package com.example.sportz.presentation.listing

import androidx.lifecycle.*
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.use_case.FetchSportsUseCase
import com.example.sportz.domain.use_case.SyncSportsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val fetchSportsUseCase: FetchSportsUseCase,
    private val syncSportsListUseCase: SyncSportsListUseCase,
) : ViewModel() {

    private val _response: MutableLiveData<Resource<List<Sports>>> = MutableLiveData()
    val response: LiveData<Resource<List<Sports>>>
        get() = _response

    init {
        viewModelScope.launch {
            fetchSportsList()
        }
    }

    suspend fun fetchSportsList() {
        fetchSportsUseCase().collect {
            when (it) {
                is Resource.Success -> {
                    insertNewData(it.data)
                    getSportsListFromLocal().let { res ->
                        _response.value = res
                    }
                }

                is Resource.Error -> {
                    getSportsListFromLocal().let { res ->
                        _response.value = res
                    }
                }

                is Resource.Loading -> {
                    _response.value = it
                }
            }
        }
    }

    suspend fun insertNewData(sports: List<Sports>?) {
        sports?.let {
            val sportsList = validateList(sports)
            clearAllData()
            syncSportsListUseCase.insertSports(sportsList)
        }
    }

    fun validateList(sports: List<Sports>): List<Sports> {
        return sports.filter { sport -> sport.name != null && sport.icon != null }.toList()
    }

    suspend fun clearAllData() {
        syncSportsListUseCase.clearAllSports()
    }

    suspend fun getSportsListFromLocal(): Resource<List<Sports>> {
        val dataFromLocal = syncSportsListUseCase.getSportsFromLocal()
        return Resource.Success(dataFromLocal)
    }
}