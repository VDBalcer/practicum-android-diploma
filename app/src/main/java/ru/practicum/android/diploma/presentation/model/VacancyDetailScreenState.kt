package ru.practicum.android.diploma.presentation.model

import ru.practicum.android.diploma.domain.models.VacancyDetailModel

sealed class VacancyDetailScreenState {
    object Loading : VacancyDetailScreenState()
    object JobNotFound : VacancyDetailScreenState()
    object ServerError : VacancyDetailScreenState()
    data class Content(
        val vacancy: VacancyDetailModel
    ) : VacancyDetailScreenState()
}
