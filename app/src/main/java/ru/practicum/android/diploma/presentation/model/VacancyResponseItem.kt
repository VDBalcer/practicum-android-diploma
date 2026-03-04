package ru.practicum.android.diploma.presentation.model

data class VacancyResponseItem(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacancyItem>,
)
