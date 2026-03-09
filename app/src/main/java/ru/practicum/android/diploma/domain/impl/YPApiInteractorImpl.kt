package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.ApiRepository
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyRequestModel
import ru.practicum.android.diploma.domain.models.VacancyResponseModel

class YPApiInteractorImpl(
    private val repository: ApiRepository,
) : ApiInteractor {
    override suspend fun getVacancy(id: String): NetworkResult<VacancyDetailModel> =
        repository.getVacancy(id)

    override suspend fun getAreas(): NetworkResult<List<FilterAreaModel>> =
        repository.getAreas()

    override suspend fun getCountries(): NetworkResult<List<FilterAreaModel>> {
        return when (val result = repository.getAreas()) {
            is NetworkResult.Success ->
                NetworkResult.Success(result.data.filter { it.parentId == 0 })
            else -> result
        }
    }

    override suspend fun searchRegions(
        countryId: Int?,
        query: String?
    ): NetworkResult<List<FilterAreaModel>> {
        return when (val result = repository.getAreas()) {
            is NetworkResult.Success -> {
                var regions = result.data
                countryId?.let { id ->
                    regions = regions.filter { it.parentId == id }
                }
                query?.takeIf { it.isNotBlank() }?.let { text ->
                    regions = regions.filter {
                        it.name.contains(text, ignoreCase = true)
                    }
                }
                NetworkResult.Success(regions)
            }
            else -> result
        }
    }

    override suspend fun getIndustries(): NetworkResult<List<FilterIndustryModel>> =
        repository.getIndustries()

    override suspend fun getVacancies(filter: VacancyRequestModel): NetworkResult<VacancyResponseModel> =
        repository.getVacancies(filter)
}
