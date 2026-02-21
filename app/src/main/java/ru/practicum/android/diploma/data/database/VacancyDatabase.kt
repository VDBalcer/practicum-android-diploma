package ru.practicum.android.diploma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.data.entity.VacancyEntity

@Database(version = 1, entities = [VacancyEntity::class])
abstract class VacancyDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
