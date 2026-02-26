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
import ru.practicum.android.diploma.util.debounce

class MainFragmentViewModel(
    private val interactor: ApiInteractor
) : ViewModel() {

    private var latestSearchQuery: String? = null

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
        val filter = VacancyFilterItem(text = searchQuery)
        searchJob = viewModelScope.launch {
            val response = interactor.getVacancies(filter.toDomain())
            processResult(response)
        }
    }

    private fun processResult(result: NetworkResult<VacancyResponseModel>) {
        val state = when (result) {
            is NetworkResult.Error -> MainScreenState.ServerError
            is NetworkResult.NetworkError -> MainScreenState.NoInternet
            is NetworkResult.Success<VacancyResponseModel> -> {
                if (result.data.vacancies.isNotEmpty()) {
                    MainScreenState.Content(
                        result.data.toItem()
                    )
                } else {
                    MainScreenState.JobNotFound
                }
            }
        }
        mainStateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
