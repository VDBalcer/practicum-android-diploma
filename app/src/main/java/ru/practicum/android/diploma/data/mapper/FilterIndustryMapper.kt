package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.FilterIndustryModel

fun FilterIndustryDto.toDomain(): FilterIndustryModel =
    FilterIndustryModel(
        id = id,
        name = name
    )
