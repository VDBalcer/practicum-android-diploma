package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.database.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem
import ru.practicum.android.diploma.presentation.states.IndustryScreenState

class FilterIndustryViewModel(
    private val apiInteractor: ApiInteractor,
    private val filterSharedPref: FilterInteractor,
) : ViewModel() {
    private val industryLiveData = MutableLiveData<IndustryScreenState>(IndustryScreenState.Loading)
    fun observeIndustryState(): LiveData<IndustryScreenState> = industryLiveData

    private val reselectLiveData = MutableLiveData(false)
    fun observeReSelectState(): LiveData<Boolean> = reselectLiveData

    private val industriesList = mutableListOf<FilteredIndustryItem>()

    private var latestSelectedIndustryId: Int = -1

    private var loadJob: Job? = null
    fun loadIndustries() {
        cancelJob()
        loadJob = viewModelScope.launch {
            latestSelectedIndustryId = filterSharedPref.getFilteredIndustryId()
            val result = apiInteractor.getIndustries()
            processResult(result)
        }
    }

    fun cancelJob() {
        loadJob?.cancel()
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
                        FilteredIndustryItem(
                            it.id,
                            it.name,
                            isChecked = it.id == latestSelectedIndustryId
                        )
                    }
                )
                IndustryScreenState.Content(industriesList)
            }
        }
        industryLiveData.postValue(state)
    }

    fun searchIndustry(query: String) {
        val newIndustriesList = if (query.isNotBlank()) {
            industriesList.filter { item ->
                item.name.contains(query, true)
            }
        } else {
            industriesList
        }

        industryLiveData.value = IndustryScreenState.Content(
            newIndustriesList
        )
    }

    private fun reSelect(checkedIndustryId: Int) {
        reselectLiveData.value = latestSelectedIndustryId != checkedIndustryId
    }

    fun checkIndustry(checkedIndustryId: Int, filterQuery: String) {
        reSelect(checkedIndustryId)
        latestSelectedIndustryId = checkedIndustryId

        val newIndustryList = industriesList.map { item ->
            FilteredIndustryItem(
                item.id,
                item.name,
                isChecked = item.id == latestSelectedIndustryId
            )
        }
        industriesList.clear()
        industriesList.addAll(newIndustryList)

        searchIndustry(filterQuery)
    }

    fun saveFilteredIndustry() {
        val filteredIndustry = industriesList.find { it.id == latestSelectedIndustryId }!!
        viewModelScope.launch {
            filterSharedPref.saveFilteredIndustry(
                FilterIndustryModel(
                    filteredIndustry.id,
                    filteredIndustry.name
                )
            )
        }
    }
}
