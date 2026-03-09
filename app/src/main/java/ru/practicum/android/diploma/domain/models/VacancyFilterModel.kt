package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

data class VacancyFilterModel(
    val salaryFrom: Int?,
    val includeWithoutSalary: Boolean,
    val industry: FilterIndustryModel?,
    val country: FilteredAreaItem? = null,
    val region: FilteredAreaItem? = null,
)
