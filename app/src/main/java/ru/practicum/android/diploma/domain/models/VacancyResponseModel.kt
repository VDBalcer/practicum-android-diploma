package ru.practicum.android.diploma.domain.models

data class VacancyResponseModel(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacancyDetailModel>,
)
