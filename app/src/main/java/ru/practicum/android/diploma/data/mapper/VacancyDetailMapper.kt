package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.VacancyDetailModel

fun VacancyDetailDto.toDomain(): VacancyDetailModel =
    VacancyDetailModel(
        id = id,
        name = name,
        description = description,
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        experience = experience?.toDomain(),
        schedule = schedule?.toDomain(),
        employment = employment?.toDomain(),
        contacts = contacts?.toDomain(),
        employer = employer.toDomain(),
        area = area.toDomain(),
        skills = skills,
        url = url,
        industry = industry.toDomain()
    )


fun VacancyDetailDto.SalaryDto.toDomain(): VacancyDetailModel.SalaryModel =
    VacancyDetailModel.SalaryModel(
        from = from,
        to = to,
        currency = currency
    )


fun VacancyDetailDto.AddressDto.toDomain(): VacancyDetailModel.AddressModel =
    VacancyDetailModel.AddressModel(
        city = city,
        street = street,
        building = building,
        fullAddress = raw
    )

fun VacancyDetailDto.ExperienceDto.toDomain(): VacancyDetailModel.ExperienceModel =
    VacancyDetailModel.ExperienceModel(
        id = id,
        name = name
    )

fun VacancyDetailDto.ScheduleDto.toDomain(): VacancyDetailModel.ScheduleModel =
    VacancyDetailModel.ScheduleModel(
        id = id,
        name = name
    )

fun VacancyDetailDto.EmploymentDto.toDomain(): VacancyDetailModel.EmploymentModel =
    VacancyDetailModel.EmploymentModel(
        id = id,
        name = name
    )

fun VacancyDetailDto.ContactsDto.toDomain(): VacancyDetailModel.ContactsModel =
    VacancyDetailModel.ContactsModel(
        id = id,
        name = name,
        email = email,
        phone = phones.map { it.formatted }
    )

fun VacancyDetailDto.EmployerDto.toDomain(): VacancyDetailModel.EmployerModel =
    VacancyDetailModel.EmployerModel(
        id = id,
        name = name,
        logo = logo
    )
