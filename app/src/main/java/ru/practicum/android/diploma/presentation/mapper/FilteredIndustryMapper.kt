package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

fun FilteredIndustryItem.toDomain(): FilterIndustryModel =
    FilterIndustryModel(
        id = id,
        name = name
    )

fun FilterIndustryModel.toItem(): FilteredIndustryItem =
    FilteredIndustryItem(
        id = id,
        name = name
    )
