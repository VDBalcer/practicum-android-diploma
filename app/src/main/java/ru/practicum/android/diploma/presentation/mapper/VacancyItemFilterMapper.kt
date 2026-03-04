package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyRequestModel
import ru.practicum.android.diploma.presentation.model.VacancyRequestItem

fun VacancyRequestItem.toDomain(): VacancyRequestModel =
    VacancyRequestModel(
        areaId = areaId,
        industryId = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )
