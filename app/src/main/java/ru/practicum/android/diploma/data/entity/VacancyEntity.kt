package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vacancy_table",
    indices = [Index(value = ["orderIndex"], unique = true)]
)
data class VacancyEntity(
    @PrimaryKey()
    val id: String, // по данному айди будем сопоставлять вакансии с полученными из сети
    val orderIndex: Int, // по данному индексу будем сортировать вакансии в списке избранного
    val name: String,
    val description: String,
    val salaryJson: String?,
    val addressJson: String?,
    val experienceJson: String?,
    val scheduleJson: String?,
    val employmentJson: String?,
    val contactsJson: String?,
    val employerJson: String?,
    val areaJson: String?,
    val skillsJson: String?,
    val url: String,
    val industryJson: String?,
)
