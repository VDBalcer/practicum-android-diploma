package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.domain.models.FilterAreaModel

fun FilterAreaDto.toDomain(): FilterAreaModel =
    FilterAreaModel(
        id = id,
        name = name,
        parentId = parentId,
        areas = areas.map { it.toDomain() }
    )
