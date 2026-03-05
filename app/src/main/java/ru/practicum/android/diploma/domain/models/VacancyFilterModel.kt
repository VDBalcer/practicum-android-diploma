package ru.practicum.android.diploma.domain.models

data class VacancyFilterModel(
    val salaryFrom: Int?,
    val includeWithoutSalary: Boolean,
    val industryId: String?,
)
