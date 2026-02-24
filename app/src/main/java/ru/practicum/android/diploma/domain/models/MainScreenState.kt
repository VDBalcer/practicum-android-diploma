package ru.practicum.android.diploma.domain.models

sealed class MainScreenState {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()

    data class Content(
        val items: List<VacancyDetailModel>
    ) : MainScreenState()
}
