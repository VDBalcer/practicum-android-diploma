package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.ApiRepository
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.domain.models.VacancyResponseModel

class YPApiInteractorImpl(
    private val repository: ApiRepository,
) : ApiInteractor {
    override suspend fun getVacancy(id: String): NetworkResult<VacancyDetailModel> =
        repository.getVacancy(id)

    override suspend fun getAreas(): NetworkResult<List<FilterAreaModel>> =
        repository.getAreas()

    override suspend fun getIndustries(): NetworkResult<List<FilterIndustryModel>> =
        repository.getIndustries()

    override suspend fun getVacancies(filter: VacancyFilterModel): NetworkResult<VacancyResponseModel> =
        repository.getVacancies(filter)
}
