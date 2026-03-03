package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import ru.practicum.android.diploma.presentation.events.ErrorType
import ru.practicum.android.diploma.presentation.events.MainScreenEvent
import ru.practicum.android.diploma.presentation.mapper.toDomain
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.model.VacancyRequestItem
import ru.practicum.android.diploma.presentation.states.MainScreenState
import ru.practicum.android.diploma.util.debounce

class MainFragmentViewModel(
    private val interactor: ApiInteractor,
) : ViewModel() {
    private var latestSearchQuery: String? = null
    private val vacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { query ->
        val filter = VacancyRequestItem(
            text = query,
            page = 1
        )
        searchRequest(filter)
    }

    private val mainStateLiveData =
        MutableLiveData<MainScreenState>(MainScreenState.StartSearch)

    private val _events = MutableSharedFlow<MainScreenEvent>()
    val events = _events.asSharedFlow()

    private fun sendErrorEvent(errorType: ErrorType) {
        viewModelScope.launch {
            _events.emit(MainScreenEvent.ShowError(errorType))
        }
    }

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

    fun searchRequest(filter: VacancyRequestItem) {
        mainStateLiveData.value = MainScreenState.Loading
        searchJob?.cancel()
        loadPage(
            filter = filter.copy(page = 1),
            isNewSearch = true
        )
    }

    private fun loadPage(
        filter: VacancyRequestItem,
        isNewSearch: Boolean,
    ) {
        searchJob = viewModelScope.launch {
            val result = interactor.getVacancies(filter.toDomain())
            processResult(result, filter, isNewSearch)
        }
    }

    private fun processResult(
        result: NetworkResult<VacancyResponseModel>,
        filter: VacancyRequestItem,
        isNewSearch: Boolean,
    ) {
        val currentState = mainStateLiveData.value
        when (result) {
            is NetworkResult.Success -> {
                val response = result.data.toItem()
                val previousVacancies = if (!isNewSearch && currentState is MainScreenState.Content) {
                    currentState.response.vacancies
                } else {
                    emptyList()
                }
                val updatedVacancies = previousVacancies + response.vacancies
                if (updatedVacancies.isEmpty()) {
                    mainStateLiveData.postValue(MainScreenState.JobNotFound)
                    return
                }
                mainStateLiveData.postValue(
                    MainScreenState.Content(
                        response = response.copy(vacancies = updatedVacancies),
                        isPaginationLoading = false, filter = filter
                    )
                )
            }

            is NetworkResult.NetworkError -> {
                if (isNewSearch) {
                    mainStateLiveData.postValue(MainScreenState.NoInternet)
                } else {
                    finishPagination()
                    sendErrorEvent(ErrorType.NO_INTERNET)
                }
            }

            is NetworkResult.Error -> {
                if (isNewSearch) {
                    mainStateLiveData.postValue(MainScreenState.ServerError)
                } else {
                    finishPagination()
                    sendErrorEvent(ErrorType.NETWORK)
                }
            }
        }
    }

    private fun finishPagination() {
        val current = mainStateLiveData.value
        if (current is MainScreenState.Content) {
            mainStateLiveData.postValue(
                current.copy(isPaginationLoading = false)
            )
        }
    }

    fun onLastItemReached() {
        val currentState = mainStateLiveData.value
        if (
            currentState !is MainScreenState.Content ||
            currentState.isPaginationLoading ||
            currentState.response.page + 1 >= currentState.response.pages
        ) {
            return
        }
        mainStateLiveData.value = currentState.copy(
            isPaginationLoading = true
        )
        loadPage(
            filter = currentState.filter.copy(
                page = currentState.response.page + 1
            ),
            isNewSearch = false
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
