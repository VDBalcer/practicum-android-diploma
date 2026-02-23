package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.domain.models.VacancyResponseModel

fun VacancyResponseDto.toDomain(): VacancyResponseModel =
    VacancyResponseModel(
        found = found,
        pages = pages,
        page = page,
        vacancies = items.map { it.toDomain() },
    )

