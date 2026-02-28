package ru.practicum.android.diploma.data.mapper

import com.google.gson.Gson
import ru.practicum.android.diploma.data.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel.EmployerModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel.SalaryModel

fun VacancyEntity.toDomain(gson: Gson): VacancyDetailModel {
    return VacancyDetailModel(
        id = id,
        name = name,
        description = description,
        salary = gson.fromJsonOrNull<SalaryModel>(salaryJson),
        address = gson.fromJsonOrNull<VacancyDetailModel.AddressModel>(addressJson),
        experience = gson.fromJsonOrNull<VacancyDetailModel.ExperienceModel>(experienceJson),
        schedule = gson.fromJsonOrNull<VacancyDetailModel.ScheduleModel>(scheduleJson),
        employment = gson.fromJsonOrNull<VacancyDetailModel.EmploymentModel>(employmentJson),
        contacts = gson.fromJsonOrNull<VacancyDetailModel.ContactsModel>(contactsJson),
        employer = gson.fromJson(employerJson, EmployerModel::class.java),
        area = gson.fromJson(areaJson, FilterAreaModel::class.java),
        skills = gson.fromJsonListOrNull<String>(skillsJson),
        url = url,
        industry = gson.fromJson(industryJson, FilterIndustryModel::class.java),
    )
}

fun VacancyDetailModel.toEntity(
    orderIndex: Int,
    gson: Gson,
): VacancyEntity {
    return VacancyEntity(
        id = id,
        orderIndex = orderIndex,
        name = name,
        description = description,
        salaryJson = gson.toJson(salary),
        addressJson = gson.toJson(address),
        experienceJson = gson.toJson(experience),
        scheduleJson = gson.toJson(schedule),
        employmentJson = gson.toJson(employment),
        contactsJson = gson.toJson(contacts),
        employerJson = gson.toJson(employer),
        areaJson = gson.toJson(area),
        skillsJson = gson.toJson(skills),
        url = url,
        industryJson = gson.toJson(industry)
    )
}
