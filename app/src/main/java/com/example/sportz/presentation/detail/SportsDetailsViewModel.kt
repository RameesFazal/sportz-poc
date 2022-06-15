package com.example.sportz.presentation.detail

import androidx.lifecycle.*
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.use_case.FetchSportsItemUseCase
import com.example.sportz.domain.use_case.SyncSportsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsDetailsViewModel @Inject constructor(
    private val fetchSportsItemUseCase: FetchSportsItemUseCase,
    private val syncSportsDetailsUseCase: SyncSportsDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _response: MutableLiveData<Resource<SportsDetails>> = MutableLiveData()
    val response: LiveData<Resource<SportsDetails>> = _response

    init {
        viewModelScope.launch {
            val sportsId: Int? = savedStateHandle["sportsId"]
            sportsId?.let {
                fetchSportsById(it)
            } ?: run {
                _response.value = Resource.Error("Not a valid sports")
            }
        }
    }

    suspend fun fetchSportsById(id: Int) {
        fetchSportsItemUseCase(id).collect {
            when (it) {
                is Resource.Success -> {
                    insertSportsDetail(it.data)
                    getSportsDetailsFromLocal(id).let { sport ->
                        _response.value = sport
                    }
                }

                is Resource.Error -> {
                    getSportsDetailsFromLocal(id).let { sport ->
                        _response.value = sport
                    }
                }

                is Resource.Loading -> {
                    _response.value = it
                }
            }
        }
    }

    suspend fun insertSportsDetail(sports: SportsDetails?) {
        sports?.let {
            syncSportsDetailsUseCase.insertSportsDetail(sports)
        }
    }

    suspend fun getSportsDetailsFromLocal(id: Int): Resource<SportsDetails> {
        val dataFromLocal = syncSportsDetailsUseCase.getSportsFromLocal(id)
        dataFromLocal?.let {
            return Resource.Success(dataFromLocal)
        } ?: run {
            return Resource.Error("No data found", null)
        }
    }
}