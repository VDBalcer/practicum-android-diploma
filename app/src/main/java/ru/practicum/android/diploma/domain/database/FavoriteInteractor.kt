package ru.practicum.android.diploma.domain.database

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetailModel

interface FavoriteInteractor {
    fun getAllFavoritesVacancy(): Flow<List<VacancyDetailModel>>

    fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetailModel?>

    suspend fun addToFavorite(vacancy: VacancyDetailModel)

    suspend fun deleteFromFavorite(vacancyId: String)

    fun isVacancyInFavorite(vacancyId: String): Flow<Boolean>
}
