package ru.practicum.android.diploma.domain.database

import ru.practicum.android.diploma.domain.models.VacancyFilterModel

interface FilterRepository {
    suspend fun saveFilter(filter: VacancyFilterModel)
    suspend fun getFilter(): VacancyFilterModel
    suspend fun clearFilter()

    suspend fun getFilteredIndustryId(): Int
}
