package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.database.FilterInteractor
import ru.practicum.android.diploma.domain.database.FilterRepository
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterInteractorImpl(
    private val repository: FilterRepository
) : FilterInteractor {

    override suspend fun saveFilter(filter: VacancyFilterModel) {
        val validatedFilter = validate(filter)
        repository.saveFilter(validatedFilter)
    }

    override suspend fun getFilter(): VacancyFilterModel {
        return repository.getFilter()
    }

    override suspend fun clearFilter() {
        repository.clearFilter()
    }

    override suspend fun getFilteredIndustryId(): Int {
        return repository.getFilteredIndustryId()
    }

    override suspend fun saveFilteredIndustry(filteredIndustry: FilterIndustryModel) {
        repository.saveFilteredIndustry(filteredIndustry)
    }

    private fun validate(filter: VacancyFilterModel): VacancyFilterModel {
        val salary = filter.salaryFrom?.takeIf { it > 0 }

        return filter.copy(
            salaryFrom = salary
        )
    }
}
