package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.MainScreenState
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterViewModel(
    private val apiInteractor: ApiInteractor,
) : ViewModel() {
    private val _state = MutableLiveData<MainScreenState>()
    val state: LiveData<MainScreenState> = _state


    private var searchJob: Job? = null

    fun searchDebounce(text: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000L)   // debounce 1 сек
            search(text)
        }
    }

    private suspend fun search(text: String) {
        if (text.isBlank()) {
            _state.value = MainScreenState.StartSearch
            return
        }

        _state.value = MainScreenState.Loading

        val result = apiInteractor.getVacancies(
            VacancyFilterModel(text = text)
        )

        _state.value = when (result) {
            is NetworkResult.Success -> {
                val vacancies = result.data.vacancies
                if (vacancies.isEmpty()) {
                    MainScreenState.JobNotFound
                } else {
                    MainScreenState.Content(vacancies)
                }
            }

            is NetworkResult.NetworkError ->
                MainScreenState.NoInternet

            is NetworkResult.Error ->
                MainScreenState.JobNotFound
        }
    }

    init {
        _state.value = MainScreenState.StartSearch
    }
}
