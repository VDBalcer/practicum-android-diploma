package ru.practicum.android.diploma.domain.models

data class VacancyFilterModel(
    val areaId: Int? = null,
    val industryId: Int? = null,
    val text: String? = null,
    val salary: Int? = null,
    val page: Int? = null,
    val onlyWithSalary: Boolean? = null,
)
