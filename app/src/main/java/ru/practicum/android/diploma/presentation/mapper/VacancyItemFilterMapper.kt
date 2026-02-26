package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.presentation.model.VacancyFilterItem

fun VacancyFilterItem.toDomain(): VacancyFilterModel =
    VacancyFilterModel(
        areaId = areaId,
        industryId = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )
