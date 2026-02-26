package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import ru.practicum.android.diploma.presentation.model.VacancyResponseItem

fun VacancyResponseModel.toItem(): VacancyResponseItem =
    VacancyResponseItem(
        found = found,
        pages = pages,
        page = page,
        vacancies = vacancies.map {
            it.toItem()
        }
    )
