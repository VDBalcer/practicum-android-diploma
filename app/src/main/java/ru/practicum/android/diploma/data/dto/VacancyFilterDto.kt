package ru.practicum.android.diploma.data.dto

data class VacancyFilterDto(
    val area: Int?,
    val industry: Int?,
    val text: String?,
    val salary: Int?,
    val page: Int?,
    val onlyWithSalary: Boolean?
)
