package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import ru.practicum.android.diploma.presentation.mapper.toDomain
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.model.MainScreenState
import ru.practicum.android.diploma.presentation.model.VacancyFilterItem
import ru.practicum.android.diploma.presentation.model.VacancyItem
import ru.practicum.android.diploma.util.debounce

class MainFragmentViewModel(
    private val interactor: ApiInteractor
) : ViewModel() {
    private var latestSearchQuery: String? = null
    private val vacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { query ->
        val filter = VacancyFilterItem(
            text = query,
            page = 0
        )
        searchRequest(filter)
    }

    private val mainStateLiveData =
        MutableLiveData<MainScreenState>(MainScreenState.StartSearch)
    fun observeMainSate(): LiveData<MainScreenState> = mainStateLiveData
    private var searchJob: Job? = null
    fun searchDebounce(currentSearchQuery: String) {
        if (latestSearchQuery == currentSearchQuery) {
            return
        }
        if (currentSearchQuery.isNotBlank()) {
            this.latestSearchQuery = currentSearchQuery
            vacancySearchDebounce(currentSearchQuery)
        }
    }

    fun searchRequest(filter: VacancyFilterItem) {
        mainStateLiveData.value = MainScreenState.Loading
        searchJob?.cancel()
        loadPage(
            filter = filter.copy(page = 0),
            isNewSearch = true
        )
    }
    private fun loadPage(
        filter: VacancyFilterItem,
        isNewSearch: Boolean
    ) {
        searchJob = viewModelScope.launch {
            val result = interactor.getVacancies(filter.toDomain())
            processResult(result, filter, isNewSearch)
        }
    }

    private fun processResult(
        result: NetworkResult<VacancyResponseModel>,
        filter: VacancyFilterItem,
        isNewSearch: Boolean
    ) {
        when (result) {
            is NetworkResult.Error -> mainStateLiveData.postValue(MainScreenState.ServerError)
            is NetworkResult.NetworkError -> mainStateLiveData.postValue(MainScreenState.NoInternet)
            is NetworkResult.Success -> {
                val item = result.data.toItem()
                val lastVacancies =
                    if (!isNewSearch && mainStateLiveData.value is MainScreenState.Content) {
                        (mainStateLiveData.value as MainScreenState.Content).vacancies
                    } else {
                        emptyList()
                    }
                val updatedList = lastVacancies + item.vacancies
                if (updatedList.isEmpty()) {
                    mainStateLiveData.postValue(MainScreenState.JobNotFound)
                } else {
                    mainStateLiveData.postValue(
                        MainScreenState.Content(
                            vacancies = updatedList,
                            currentPage = item.page,
                            totalPages = item.pages,
                            isPaginationLoading = false,
                            filter = filter,
                            found = item.found
                        )
                    )
                }
            }
        }
    }
    fun onLastItemReached() {
        val currentState = mainStateLiveData.value
        if (currentState !is MainScreenState.Content) return
        if (currentState.isPaginationLoading) return
        if (currentState.currentPage + 1 >= currentState.totalPages) return
        mainStateLiveData.value = currentState.copy(
            isPaginationLoading = true
        )
        loadPage(
            filter = currentState.filter.copy(page = currentState.currentPage + 1),
            isNewSearch = false
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
