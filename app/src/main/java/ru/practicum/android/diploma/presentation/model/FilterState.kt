package ru.practicum.android.diploma.presentation.model

import ru.practicum.android.diploma.domain.models.VacancyFilterModel

sealed class FilterState {
    object NotEditedState : FilterState()
    data class EditedState(
        val filter: VacancyFilterModel,
    ) : FilterState()
}
