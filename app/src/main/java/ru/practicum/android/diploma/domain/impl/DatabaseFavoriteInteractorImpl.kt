package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.database.FavoriteInteractor
import ru.practicum.android.diploma.domain.database.FavoriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailModel

class DatabaseFavoriteInteractorImpl(
    private val repository: FavoriteRepository,
) : FavoriteInteractor {
    override fun getAllFavoritesVacancy(): Flow<List<VacancyDetailModel>> =
        repository.getAllFavoritesVacancy()

    override fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetailModel?> =
        repository.getFavoriteVacancyById(vacancyId)

    override suspend fun addToFavorite(vacancy: VacancyDetailModel) {
        repository.addToFavorite(vacancy)
    }

    override suspend fun deleteFromFavorite(vacancyId: String) {
        repository.deleteFromFavorite(vacancyId)
    }

    override fun isVacancyInFavorite(vacancyId: String): Flow<Boolean> =
        repository.isVacancyInFavorite(vacancyId)
}
