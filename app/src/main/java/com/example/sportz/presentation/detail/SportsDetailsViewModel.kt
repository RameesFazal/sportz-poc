package com.example.sportz.presentation.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.sportz.common.Resource
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.use_case.GetSportsDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsDetailsViewModel @Inject constructor(
    private val getSportsDetail: GetSportsDetail,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<Resource<SportsDetails>> = MutableLiveData()
    val response: LiveData<Resource<SportsDetails>> = _response

    fun fetchSportsList(id: Int) = viewModelScope.launch {
        getSportsDetail(id).collect { values ->
            _response.value = values
        }
    }
}