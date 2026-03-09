package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

sealed class FilterPlaceState {
    object PlaceNotFound : FilterPlaceState()
    object Loading : FilterPlaceState()
    object ServerError : FilterPlaceState()
    data class Content(
        val areas: List<FilteredAreaItem>,
    ) : FilterPlaceState()
}
