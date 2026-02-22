package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponseDto

interface YPApiService {

    @GET("areas")
    suspend fun getAreas(): List<FilterAreaDto>

    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustryDto>

    @GET("vacancies")
    suspend fun getVacancies(
        @QueryMap options: Map<String, String>,
    ): VacancyResponseDto

    @GET("vacancies/{id}")
    suspend fun getVacanciesById(
        @Path("id") id: Int,
    ): VacancyDetailDto

}
