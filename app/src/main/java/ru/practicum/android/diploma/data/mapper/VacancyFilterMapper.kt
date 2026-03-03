package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyFilterDto
import ru.practicum.android.diploma.domain.models.VacancyRequestModel

fun VacancyRequestModel.toDto(): VacancyFilterDto =
    VacancyFilterDto(
        area = areaId,
        industry = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )
