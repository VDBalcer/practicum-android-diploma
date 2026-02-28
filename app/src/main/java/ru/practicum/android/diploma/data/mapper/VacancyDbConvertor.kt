package ru.practicum.android.diploma.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.FilterAreaModel
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel.EmployerModel
import ru.practicum.android.diploma.domain.models.VacancyDetailModel.SalaryModel

class VacancyDbConvertor(private val gson: Gson) {
    fun map(vacancy: VacancyDetailModel, orderIndex: Int): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            orderIndex = orderIndex,
            name = vacancy.name,
            description = vacancy.description,
            salaryJson = gson.toJson(vacancy.salary),
            addressJson = gson.toJson(vacancy.address),
            experienceJson = gson.toJson(vacancy.experience),
            scheduleJson = gson.toJson(vacancy.schedule),
            employmentJson = gson.toJson(vacancy.employment),
            contactsJson = gson.toJson(vacancy.contacts),
            employerJson = gson.toJson(vacancy.employer),
            areaJson = gson.toJson(vacancy.area),
            skillsJson = gson.toJson(vacancy.skills),
            url = vacancy.url,
            industryJson = gson.toJson(vacancy.industry)
        )
    }

    fun map(entity: VacancyEntity): VacancyDetailModel {
        return VacancyDetailModel(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            salary = gson.fromJsonOrNull<SalaryModel>(entity.salaryJson),
            address = gson.fromJsonOrNull<VacancyDetailModel.AddressModel>(entity.addressJson),
            experience = gson.fromJsonOrNull<VacancyDetailModel.ExperienceModel>(entity.experienceJson),
            schedule = gson.fromJsonOrNull<VacancyDetailModel.ScheduleModel>(entity.scheduleJson),
            employment = gson.fromJsonOrNull<VacancyDetailModel.EmploymentModel>(entity.employmentJson),
            contacts = gson.fromJsonOrNull<VacancyDetailModel.ContactsModel>(entity.contactsJson),
            employer = gson.fromJson(entity.employerJson, EmployerModel::class.java),
            area = gson.fromJson(entity.areaJson, FilterAreaModel::class.java),
            skills = gson.fromJsonListOrNull<String>(entity.skillsJson),
            url = entity.url,
            industry = gson.fromJson(entity.industryJson, FilterIndustryModel::class.java),
        )
    }

    private inline fun <reified T> Gson.fromJsonOrNull(json: String?): T? {
        if (json.isNullOrBlank()) return null
        return fromJson(json, T::class.java)
    }

    private inline fun <reified T> Gson.fromJsonListOrNull(json: String?): List<T> {
        val type = object : TypeToken<List<T>>() {}.type
        return fromJson(json, type)
    }
}
