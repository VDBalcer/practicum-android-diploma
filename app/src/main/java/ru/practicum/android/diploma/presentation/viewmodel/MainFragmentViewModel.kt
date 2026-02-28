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

    private var currentPage = 0
    private var totalPages = 0
    private var currentQuery: String? = null
    private var isLoadingNextPage = false
    private var latestSearchQuery: String? = null
    private val vacancyList = mutableListOf<VacancyItem>()
    private val vacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { changedText ->
        searchRequest(changedText)
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

    fun searchRequest(searchQuery: String) {
        mainStateLiveData.value = MainScreenState.Loading
        searchJob?.cancel()
        currentQuery = searchQuery
        currentPage = 0
        totalPages = 0
        vacancyList.clear()
        loadPage(0)
    }
    private fun loadPage(page: Int) {
        val query = currentQuery ?: return
        isLoadingNextPage = true
        val filter = VacancyFilterItem(
            text = query,
            page = page
        )
        searchJob = viewModelScope.launch {
            val response = interactor.getVacancies(filter.toDomain())
            processResult(response)
        }
    }

    private fun processResult(result: NetworkResult<VacancyResponseModel>) {
        isLoadingNextPage = false
        when (result) {
            is NetworkResult.Error -> mainStateLiveData.postValue(MainScreenState.ServerError)
            is NetworkResult.NetworkError -> mainStateLiveData.postValue(MainScreenState.NoInternet)
            is NetworkResult.Success -> {
                val item = result.data.toItem()
                currentPage = item.page
                totalPages = item.pages
                vacancyList.addAll(item.vacancies)
                if (vacancyList.isEmpty())
                    mainStateLiveData.postValue(MainScreenState.JobNotFound)
                else
                    mainStateLiveData.postValue(
                        MainScreenState.Content(
                            item.copy(
                                vacancies = vacancyList.toList()
                            )
                        )
                    )
            }
        }
    }
    fun onLastItemReached() {
        if (isLoadingNextPage) return
        if (currentPage + 1 >= totalPages) return
        mainStateLiveData.value = MainScreenState.PaginationLoading
        loadPage(currentPage + 1)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
