package com.example.sportz.presentation.listing

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.use_case.GetSports
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val getSports: GetSports,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<Resource<List<Sports>>> = MutableLiveData()
    val response: LiveData<Resource<List<Sports>>> = _response

    init {
        fetchSportsList()
    }

    private fun fetchSportsList() = viewModelScope.launch {
        getSports().collect { values->
            _response.value = values
        }
    }
}