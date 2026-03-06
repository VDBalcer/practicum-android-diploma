package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.VacancyRequestItem
import ru.practicum.android.diploma.presentation.model.VacancyResponseItem

sealed class MainScreenState(
    open var filter: VacancyRequestItem? = null
) {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()
    object ServerError : MainScreenState()

    data class Content(
        val response: VacancyResponseItem,
        val isPaginationLoading: Boolean,
        override var filter: VacancyRequestItem?
    ) : MainScreenState(filter)
}
