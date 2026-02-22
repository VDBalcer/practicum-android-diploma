package ru.practicum.android.diploma.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.entity.VacancyEntity

@Dao
interface VacancyDao {
    // Получить список всех избранных вакансий
    @Query("SELECT * FROM vacancy_table ORDER BY orderIndex ASC")
    fun getVacancies(): Flow<List<VacancyEntity>>

    // Вспомогательная функция для сортировки вакансий по добавлению
    @Query("SELECT MAX(orderIndex) FROM vacancy_table")
    fun getMaxOrderIndex(): Int?

    // Получить отдельную избранную вакансию по идентификатору
    @Query("SELECT * FROM vacancy_table WHERE id = :id")
    fun getVacancyById(id: String): Flow<VacancyEntity>

    // Добавление вакансии в БД
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: VacancyEntity)

    // Удаление вакансии из БД по id
    @Query("DELETE FROM vacancy_table WHERE id = :id")
    fun deleteVacancyById(id: String)
}
