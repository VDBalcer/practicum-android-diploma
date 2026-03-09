package ru.practicum.android.diploma.domain.database

import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

interface FilterInteractor {
    suspend fun saveFilter(filter: VacancyFilterModel)
    suspend fun getFilter(): VacancyFilterModel
    suspend fun clearFilter()

    suspend fun getFilteredIndustryId(): Int
    suspend fun saveFilteredIndustry(filteredIndustry: FilterIndustryModel)
}
