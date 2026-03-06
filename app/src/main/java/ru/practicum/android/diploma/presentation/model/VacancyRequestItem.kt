package ru.practicum.android.diploma.presentation.model

data class VacancyRequestItem(
    val areaId: Int? = null,
    val industryId: Int? = null,
    val text: String? = null,
    val salary: Int? = null,
    val page: Int = 1,
    val onlyWithSalary: Boolean = false,
)
