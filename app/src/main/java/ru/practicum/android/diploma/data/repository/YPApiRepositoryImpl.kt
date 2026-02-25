package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.NetworkMonitor
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.mapper.toDto
import ru.practicum.android.diploma.data.network.YPApiService
import ru.practicum.android.diploma.domain.api.ApiRepository
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.domain.models.VacancyResponseModel
import java.io.IOException

class YPApiRepositoryImpl(
    private val api: YPApiService,
    private val networkMonitor: NetworkMonitor
) : ApiRepository {

    override suspend fun getVacancy(id: String): NetworkResult<VacancyDetailModel> =
        safeApiCall { api.getVacanciesById(id).toDomain() }

    override suspend fun getAreas(): NetworkResult<List<FilterAreaModel>> =
        safeApiCall { api.getAreas().map { it.toDomain() } }

    override suspend fun getIndustries(): NetworkResult<List<FilterIndustryModel>> =
        safeApiCall { api.getIndustries().map { it.toDomain() } }

    override suspend fun getVacancies(
        filter: VacancyFilterModel,
    ): NetworkResult<VacancyResponseModel> =
        safeApiCall {
            val dto = filter.toDto()

            api.getVacancies(
                area = dto.area,
                industry = dto.industry,
                text = dto.text,
                salary = dto.salary,
                page = dto.page,
                onlyWithSalary = dto.onlyWithSalary
            ).toDomain()
        }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): NetworkResult<T> {
        if (!networkMonitor.isConnected()) {
            return NetworkResult.NetworkError(
                IOException("Отсутствует интернет соединение")
            )
        }

        return try {
            val result = apiCall()
            NetworkResult.Success(result)
        } catch (e: HttpException) {
            NetworkResult.Error(
                code = e.code(),
                message = e.response()?.errorBody()?.string()
            )
        } catch (e: IOException) {
            NetworkResult.NetworkError(e)
        } catch (e: CancellationException) {
            throw e
        }
    }
}
