package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyRequestDto
import ru.practicum.android.diploma.domain.models.VacancyRequestModel

fun VacancyRequestModel.toDto(): VacancyRequestDto =
    VacancyRequestDto(
        area = areaId,
        industry = industryId,
        text = text,
        salary = salary,
        page = page,
        onlyWithSalary = onlyWithSalary
    )

fun VacancyRequestDto.toQueryMap(): Map<String, String> {
    return buildMap {
        area?.let { put("area", it.toString()) }
        industry?.let { put("industry", it.toString()) }
        text?.let { put("text", it) }
        salary?.let { put("salary", it.toString()) }
        page?.let { put("page", it.toString()) }
        onlyWithSalary?.let { put("only_with_salary", it.toString()) }
    }
}
