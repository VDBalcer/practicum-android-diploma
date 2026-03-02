package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.VacancyFilterItem
import ru.practicum.android.diploma.presentation.model.VacancyResponseItem

sealed class MainScreenState {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()
    object ServerError : MainScreenState()

    data class Content(
        val response: VacancyResponseItem,
        val isPaginationLoading: Boolean,
        val filter: VacancyFilterItem
    ) : MainScreenState()
}
