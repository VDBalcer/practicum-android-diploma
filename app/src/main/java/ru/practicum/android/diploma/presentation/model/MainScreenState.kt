package ru.practicum.android.diploma.presentation.model

sealed class MainScreenState {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()

    data class Content(
        val item: VacancyResponseItem
    ) : MainScreenState()
}
