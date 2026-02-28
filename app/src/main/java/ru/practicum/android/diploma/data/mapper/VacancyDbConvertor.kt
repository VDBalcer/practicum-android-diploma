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
            salary = entity.salaryJson?.let {
                gson.fromJson(it, SalaryModel::class.java)
            },
            address = entity.addressJson?.let {
                gson.fromJson(it, VacancyDetailModel.AddressModel::class.java)
            },
            experience = entity.experienceJson?.let {
                gson.fromJson(it, VacancyDetailModel.ExperienceModel::class.java)
            },
            schedule = entity.scheduleJson?.let {
                gson.fromJson(it, VacancyDetailModel.ScheduleModel::class.java)
            },
            employment = entity.employmentJson?.let {
                gson.fromJson(it, VacancyDetailModel.EmploymentModel::class.java)
            },
            contacts = entity.contactsJson?.let {
                gson.fromJson(it, VacancyDetailModel.ContactsModel::class.java)
            },
            employer = entity.employerJson.let {
                gson.fromJson(it, EmployerModel::class.java)
            },
            area = entity.areaJson.let {
                gson.fromJson(it, FilterAreaModel::class.java)
            },
            skills = entity.skillsJson.let {
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson<List<String>>(it, type)
            },
            url = entity.url,
            industry = entity.industryJson.let {
                gson.fromJson(it, FilterIndustryModel::class.java)
            }
        )
    }
}
