package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyItem

fun VacancyItem.toDomain(): VacancyDetailModel =
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

fun VacancyItem.SalaryItem.toDomain(): VacancyDetailModel.SalaryModel =
    VacancyDetailModel.SalaryModel(
        from = from,
        to = to,
        currency = currency
    )

fun VacancyItem.AddressItem.toDomain(): VacancyDetailModel.AddressModel =
    VacancyDetailModel.AddressModel(
        city = city,
        street = street,
        building = building,
        fullAddress = fullAddress
    )

fun VacancyItem.ExperienceItem.toDomain(): VacancyDetailModel.ExperienceModel =
    VacancyDetailModel.ExperienceModel(
        id = id,
        name = name
    )

fun VacancyItem.ScheduleItem.toDomain(): VacancyDetailModel.ScheduleModel =
    VacancyDetailModel.ScheduleModel(
        id = id,
        name = name
    )

fun VacancyItem.EmploymentItem.toDomain(): VacancyDetailModel.EmploymentModel =
    VacancyDetailModel.EmploymentModel(
        id = id,
        name = name
    )

fun VacancyItem.ContactsItem.toDomain(): VacancyDetailModel.ContactsModel =
    VacancyDetailModel.ContactsModel(
        id = id,
        name = name,
        email = email,
        phone = phone
    )

fun VacancyItem.EmployerItem.toDomain(): VacancyDetailModel.EmployerModel =
    VacancyDetailModel.EmployerModel(
        id = id,
        name = name,
        logo = logo
    )

fun VacancyDetailModel.toItem(): VacancyItem =
    VacancyItem(
        id = id,
        name = name,
        description = description,
        salary = salary?.toItem(),
        address = address?.toItem(),
        experience = experience?.toItem(),
        schedule = schedule?.toItem(),
        employment = employment?.toItem(),
        contacts = contacts?.toItem(),
        employer = employer.toItem(),
        area = area.toItem(),
        skills = skills,
        url = url,
        industry = industry.toItem()
    )

fun VacancyDetailModel.SalaryModel.toItem(): VacancyItem.SalaryItem =
    VacancyItem.SalaryItem(
        from = from,
        to = to,
        currency = currency
    )

fun VacancyDetailModel.AddressModel.toItem(): VacancyItem.AddressItem =
    VacancyItem.AddressItem(
        city = city,
        street = street,
        building = building,
        fullAddress = fullAddress
    )

fun VacancyDetailModel.ExperienceModel.toItem(): VacancyItem.ExperienceItem =
    VacancyItem.ExperienceItem(
        id = id,
        name = name
    )

fun VacancyDetailModel.ScheduleModel.toItem(): VacancyItem.ScheduleItem =
    VacancyItem.ScheduleItem(
        id = id,
        name = name
    )

fun VacancyDetailModel.EmploymentModel.toItem(): VacancyItem.EmploymentItem =
    VacancyItem.EmploymentItem(
        id = id,
        name = name
    )

fun VacancyDetailModel.ContactsModel.toItem(): VacancyItem.ContactsItem =
    VacancyItem.ContactsItem(
        id = id,
        name = name,
        email = email,
        phone = phone
    )

fun VacancyDetailModel.EmployerModel.toItem(): VacancyItem.EmployerItem =
    VacancyItem.EmployerItem(
        id = id,
        name = name,
        logo = logo
    )
