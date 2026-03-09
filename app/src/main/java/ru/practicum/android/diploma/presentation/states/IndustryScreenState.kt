package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

sealed class IndustryScreenState {
    object Loading : IndustryScreenState()
    object NoInternet : IndustryScreenState()
    object ServerError : IndustryScreenState()

    data class Content(
        val industries: List<FilteredIndustryItem>,
        val isIndustryReSelected: Boolean
    ) : IndustryScreenState()
}
