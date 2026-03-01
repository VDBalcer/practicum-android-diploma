package ru.practicum.android.diploma.presentation.model

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
