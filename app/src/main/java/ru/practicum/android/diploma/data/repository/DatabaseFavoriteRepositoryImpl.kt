package ru.practicum.android.diploma.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.database.VacancyDatabase
import ru.practicum.android.diploma.data.mapper.toDomain
import ru.practicum.android.diploma.data.mapper.toEntity
import ru.practicum.android.diploma.domain.database.FavoriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailModel

class DatabaseFavoriteRepositoryImpl(
    private val database: VacancyDatabase,
    private val gson: Gson,
) : FavoriteRepository {
    override fun getAllFavoritesVacancy(): Flow<List<VacancyDetailModel>> =
        database.vacancyDao().getVacancies()
            .map { entities ->
                entities.map { it.toDomain(gson) }
            }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetailModel?> =
        database.vacancyDao().getVacancyById(vacancyId)
            .map { entity ->
                entity?.toDomain(gson)
            }

    override suspend fun addToFavorite(vacancy: VacancyDetailModel) {
        withContext(Dispatchers.IO) {
            val maxIndex: Int = database.vacancyDao().getMaxOrderIndex() ?: 0
            database.vacancyDao().insert(
                vacancy.toEntity(maxIndex + 1, gson)
            )
        }
    }

    override suspend fun deleteFromFavorite(vacancyId: String) {
        withContext(Dispatchers.IO) {
            database.vacancyDao().deleteVacancyById(vacancyId)
        }
    }

    override fun isVacancyInFavorite(vacancyId: String): Flow<Boolean> =
        database.vacancyDao().getVacancyById(vacancyId).map { entity ->
            entity != null
        }

}
