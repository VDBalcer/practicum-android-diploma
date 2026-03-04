package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyRequestDto
import ru.practicum.android.diploma.domain.models.VacancyRequestModel

fun VacancyRequestModel.toDto(): VacancyRequestDto =
    VacancyRequestDto(
        area = areaId,
        industry = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )
