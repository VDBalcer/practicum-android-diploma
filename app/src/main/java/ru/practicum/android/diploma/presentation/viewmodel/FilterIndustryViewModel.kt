package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.database.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem
import ru.practicum.android.diploma.presentation.states.IndustryScreenState

class FilterIndustryViewModel(
    private val apiInteractor: ApiInteractor,
    private val filterSharedPref: FilterInteractor,
) : ViewModel() {
    private val industryLiveData = MutableLiveData<IndustryScreenState>(IndustryScreenState.Loading)
    fun observeIndustryState(): LiveData<IndustryScreenState> = industryLiveData

    val industriesList = mutableListOf<FilteredIndustryItem>()

    init {
        viewModelScope.launch {
            val result = apiInteractor.getIndustries()
            processResult(result)
        }
    }

    private fun processResult(
        result: NetworkResult<List<FilterIndustryModel>>
    ) {
        val state = when (result) {
            is NetworkResult.Error -> IndustryScreenState.ServerError
            is NetworkResult.NetworkError -> IndustryScreenState.NoInternet
            is NetworkResult.Success -> {
                industriesList.addAll(
                    result.data.map {
                        it.toItem()
                    }
                )
                IndustryScreenState.Content(industriesList, false)
            }
        }
        industryLiveData.postValue(state)
    }
}
