package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyFilterDto
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

fun VacancyFilterModel.toDto(): VacancyFilterDto =
    VacancyFilterDto(
        area = areaId,
        industry = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )
