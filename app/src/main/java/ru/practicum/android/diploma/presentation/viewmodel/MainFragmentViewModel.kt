package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import ru.practicum.android.diploma.presentation.mapper.VacancyResponseItemMapper
import ru.practicum.android.diploma.presentation.mapper.toDomain
import ru.practicum.android.diploma.presentation.model.MainScreenState
import ru.practicum.android.diploma.presentation.model.VacancyFilterItem
import ru.practicum.android.diploma.util.debounce

class MainFragmentViewModel(
    private val interactor: ApiInteractor,
    private val responseMapper: VacancyResponseItemMapper
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

        val filter = VacancyFilterItem(text = searchQuery)
        viewModelScope.launch {
            val response = interactor.getVacancies(filter.toDomain())
            processResult(response)
        }
    }

    private fun processResult(result: NetworkResult<VacancyResponseModel>) {
        when (result) {
            is NetworkResult.Error -> mainStateLiveData.postValue(MainScreenState.StartSearch)
            is NetworkResult.NetworkError -> mainStateLiveData.postValue(MainScreenState.StartSearch)
            is NetworkResult.Success<VacancyResponseModel> -> mainStateLiveData.postValue(
                MainScreenState.Content(
                    responseMapper.mapFromDomain((result.data))
                )
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
