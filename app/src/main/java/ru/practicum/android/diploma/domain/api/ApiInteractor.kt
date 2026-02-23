package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.domain.models.VacancyResponseModel

interface ApiInteractor {
    suspend fun getVacancy(id: String): NetworkResult<VacancyDetailModel>

    suspend fun getAreas(): NetworkResult<List<FilterAreaModel>>

    suspend fun getIndustries(): NetworkResult<List<FilterIndustryModel>>

    suspend fun getVacancies(
        filter: VacancyFilterModel,
    ): NetworkResult<VacancyResponseModel>
}
