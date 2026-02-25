package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

class FilteredIndustryMapper {
    fun mapToDomain(item: FilteredIndustryItem): FilterIndustryModel =
        FilterIndustryModel(
            id = item.id,
            name = item.name
        )

    fun mapFromDomain(model: FilterIndustryModel): FilteredIndustryItem =
        FilteredIndustryItem(
            id = model.id,
            name = model.name
        )
}
