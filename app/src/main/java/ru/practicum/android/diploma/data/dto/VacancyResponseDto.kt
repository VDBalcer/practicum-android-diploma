package ru.practicum.android.diploma.data.dto

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDetailDto>,
)
