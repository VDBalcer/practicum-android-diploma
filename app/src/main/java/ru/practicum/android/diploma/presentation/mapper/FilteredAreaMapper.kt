package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

fun FilteredAreaItem.toDomain(): FilterAreaModel =
    FilterAreaModel(
        id = id,
        name = name,
        parentId = parentId,
        areas = areas.map { it.toDomain() }
    )

fun FilterAreaModel.toItem(): FilteredAreaItem =
    FilteredAreaItem(
        id = id,
        name = name,
        parentId = parentId,
        areas = areas.map { it.toItem() }
    )
