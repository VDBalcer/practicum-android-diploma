package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import ru.practicum.android.diploma.presentation.model.VacancyResponseItem

class VacancyResponseItemMapper(
    private val itemMapper: VacancyItemMapper
) {
    fun mapFromDomain(model: VacancyResponseModel) =
        VacancyResponseItem(
            found = model.found,
            pages = model.pages,
            page = model.page,
            vacancies = model.vacancies.map {
                itemMapper.mapFromDomain(it)
            }
        )
}
