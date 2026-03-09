package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.states.FilterPlaceState

class FilterPlaceCountryViewModel(
    private val interactor: ApiInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<FilterPlaceState>()
    fun observeState(): LiveData<FilterPlaceState> = stateLiveData

    init {
        loadCountries()
    }

    private fun loadCountries() {
        stateLiveData.value = FilterPlaceState.Loading
        viewModelScope.launch {
            when (val result = interactor.getAreas()) {

                is NetworkResult.Success -> {
                    val areas = result.data.map { it.toItem() }

                    if (areas.isEmpty()) {
                        stateLiveData.postValue(FilterPlaceState.PlaceNotFound)
                    } else {
                        stateLiveData.postValue(
                            FilterPlaceState.Content(areas)
                        )
                    }
                }

                is NetworkResult.NetworkError -> {
                    stateLiveData.postValue(FilterPlaceState.ServerError)
                }

                is NetworkResult.Error -> {
                    stateLiveData.postValue(FilterPlaceState.ServerError)
                }
            }
        }
    }
}
