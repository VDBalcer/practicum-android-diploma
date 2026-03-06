package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.VacancyResponseItem

sealed class MainScreenState {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()
    object ServerError : MainScreenState()

    data class Content(
        var response: VacancyResponseItem,
        var isPaginationLoading: Boolean,
    ) : MainScreenState()
}
