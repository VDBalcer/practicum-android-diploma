package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.data.network.NetworkResult
import ru.practicum.android.diploma.data.network.YPApiService
import java.io.IOException

class YPApiRepository(
    private val api: YPApiService,
) {

    suspend fun getVacancy(id: Int): NetworkResult<VacancyDetailDto> =
        safeApiCall { api.getVacanciesById(id) }

    suspend fun getAreas(): NetworkResult<List<FilterAreaDto>> =
        safeApiCall { api.getAreas() }

    suspend fun getIndustries(): NetworkResult<List<FilterIndustryDto>> =
        safeApiCall { api.getIndustries() }

    suspend fun getVacancies(
        options: Map<String, String>,
    ): NetworkResult<VacancyResponseDto> =
        safeApiCall { api.getVacancies(options) }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): NetworkResult<T> {
        return try {
            val result = apiCall()
            NetworkResult.Success(result)
        } catch (e: IOException) {
            NetworkResult.NetworkError(e)
        } catch (e: HttpException) {
            NetworkResult.Error(
                code = e.code(),
                message = e.response()?.errorBody()?.string()
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: RuntimeException) {
            NetworkResult.Error(
                code = -1,
                message = e.localizedMessage
            )
        }
    }
}
