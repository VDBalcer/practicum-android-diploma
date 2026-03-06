package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.database.FilterLocalDataSource
import ru.practicum.android.diploma.domain.database.FilterRepository
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterRepositoryImpl(
    private val localDataSource: FilterLocalDataSource
) : FilterRepository {

    override suspend fun saveFilter(filter: VacancyFilterModel) {
        localDataSource.saveFilter(filter)
    }

    override suspend fun getFilter(): VacancyFilterModel {
        return localDataSource.getFilter()
    }

    override suspend fun clearFilter() {
        localDataSource.clearFilter()
    }
}
