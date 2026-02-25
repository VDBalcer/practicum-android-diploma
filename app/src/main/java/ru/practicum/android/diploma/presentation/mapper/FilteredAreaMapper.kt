package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

class FilteredAreaMapper {
    fun mapToDomain(item: FilteredAreaItem): FilterAreaModel =
        FilterAreaModel(
            id = item.id,
            name = item.name,
            parentId = item.parentId,
            areas = item.areas.map { mapToDomain(it) }
        )

    fun mapFromDomain(model: FilterAreaModel): FilteredAreaItem =
        FilteredAreaItem(
            id = model.id,
            name = model.name,
            parentId = model.parentId,
            areas = model.areas.map { mapFromDomain(it) }
        )
}
