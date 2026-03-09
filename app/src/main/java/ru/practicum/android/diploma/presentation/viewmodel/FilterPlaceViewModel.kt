package ru.practicum.android.diploma.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem
import ru.practicum.android.diploma.presentation.states.FilterPlaceState

class FilterPlaceViewModel(
    private val interactor: ApiInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<FilterPlaceState>()
    fun observeState(): LiveData<FilterPlaceState> = stateLiveData
    private var selectedCountry: FilteredAreaItem? = null
    private var countries: List<FilteredAreaItem> = emptyList()

    fun selectCountry(country: FilteredAreaItem) {
        selectedCountry = country
    }

    fun loadCountries() {
        if (countries.isNotEmpty()) {
            stateLiveData.value = FilterPlaceState.Content(countries)
            return
        }
        viewModelScope.launch {
            stateLiveData.value = FilterPlaceState.Loading
            val result = interactor.getCountries()
            when (result) {
                is NetworkResult.Success -> {
                    countries = result.data.map { it.toItem() }
                    stateLiveData.value = FilterPlaceState.Content(countries)
                }
                else -> {
                    stateLiveData.value = FilterPlaceState.ServerError
                }
            }
        }
    }

    fun loadRegions() {
        val regions = selectedCountry?.areas ?: emptyList()
        if (regions.isEmpty()) {
            stateLiveData.value = FilterPlaceState.PlaceNotFound
        } else {
            stateLiveData.value = FilterPlaceState.Content(regions)
        }
    }


//    fun searchRegion(query: String) {
//        viewModelScope.launch {
//            when (val result = interactor.searchRegions(selectedCountryId, query)) {
//                is NetworkResult.Success -> {
//                    val regions = result.data.map { it.toItem() }
//                    if (regions.isEmpty()) {
//                        stateLiveData.postValue(FilterPlaceState.PlaceNotFound)
//                    } else {
//                        stateLiveData.postValue(
//                            FilterPlaceState.Content(regions)
//                        )
//                    }
//                }
//                is NetworkResult.NetworkError -> {
//                    stateLiveData.postValue(FilterPlaceState.ServerError)
//                }
//                is NetworkResult.Error -> {
//                    stateLiveData.postValue(FilterPlaceState.ServerError)
//                }
//            }
//        }
//    }
}
