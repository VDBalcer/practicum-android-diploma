package ru.practicum.android.diploma.presentation.mapper

import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyItem

class VacancyItemMapper(
    private val areaMapper: FilteredAreaMapper,
    private val industryMapper: FilteredIndustryMapper,
) {
    fun mapToDomain(item: VacancyItem): VacancyDetailModel =
        VacancyDetailModel(
            id = item.id,
            name = item.name,
            description = item.description,
            salary = mapSalaryToDomain(item = item.salary),
            address = mapAddressToDomain(item = item.address),
            experience = mapExperienceToDomain(item = item.experience),
            schedule = mapScheduleToDomain(item = item.schedule),
            employment = mapEmploymentToDomain(item = item.employment),
            contacts = mapContactsToDomain(item = item.contacts),
            employer = mapEmployerToDomain(item = item.employer),
            area = areaMapper.mapToDomain(item.area),
            skills = item.skills,
            url = item.url,
            industry = industryMapper.mapToDomain(item.industry)
        )

    private fun mapSalaryToDomain(item: VacancyItem.SalaryItem?): VacancyDetailModel.SalaryModel? =
        if (item != null) {
            VacancyDetailModel.SalaryModel(
                from = item.from,
                to = item.to,
                currency = item.currency
            )
        } else {
            null
        }

    private fun mapAddressToDomain(item: VacancyItem.AddressItem?): VacancyDetailModel.AddressModel? =
        if (item != null) {
            VacancyDetailModel.AddressModel(
                city = item.city,
                street = item.street,
                building = item.building,
                fullAddress = item.fullAddress
            )
        } else {
            null
        }

    private fun mapExperienceToDomain(item: VacancyItem.ExperienceItem?): VacancyDetailModel.ExperienceModel? =
        if (item != null) {
            VacancyDetailModel.ExperienceModel(
                id = item.id,
                name = item.name
            )
        } else {
            null
        }

    private fun mapScheduleToDomain(item: VacancyItem.ScheduleItem?): VacancyDetailModel.ScheduleModel? =
        if (item != null) {
            VacancyDetailModel.ScheduleModel(
                id = item.id,
                name = item.name
            )
        } else {
            null
        }

    private fun mapEmploymentToDomain(item: VacancyItem.EmploymentItem?): VacancyDetailModel.EmploymentModel? =
        if (item != null) {
            VacancyDetailModel.EmploymentModel(
                id = item.id,
                name = item.name
            )
        } else {
            null
        }

    private fun mapContactsToDomain(item: VacancyItem.ContactsItem?): VacancyDetailModel.ContactsModel? =
        if (item != null) {
            VacancyDetailModel.ContactsModel(
                id = item.id,
                name = item.name,
                email = item.email,
                phones = item.phone.map { VacancyDetailModel.PhoneModel(it) }
            )
        } else {
            null
        }

    private fun mapEmployerToDomain(item: VacancyItem.EmployerItem): VacancyDetailModel.EmployerModel =
        VacancyDetailModel.EmployerModel(
            id = item.id,
            name = item.name,
            logo = item.logo
        )

    fun mapFromDomain(model: VacancyDetailModel): VacancyItem =
        VacancyItem(
            id = model.id,
            name = model.name,
            description = model.description,
            salary = mapSalaryFromDomain(model.salary),
            address = mapAddressFromDomain(model.address),
            experience = mapExperienceFromDomain(model.experience),
            schedule = mapScheduleFromDomain(model.schedule),
            employment = mapEmploymentFromDomain(model.employment),
            contacts = mapContactsFromDomain(model.contacts),
            employer = mapEmployerFromDomain(model.employer),
            area = areaMapper.mapFromDomain(model.area),
            skills = model.skills,
            url = model.url,
            industry = industryMapper.mapFromDomain(model.industry)
        )

    private fun mapSalaryFromDomain(model: VacancyDetailModel.SalaryModel?): VacancyItem.SalaryItem? =
        if (model != null) {
            VacancyItem.SalaryItem(
                from = model.from,
                to = model.to,
                currency = model.currency
            )
        } else {
            null
        }

    private fun mapAddressFromDomain(model: VacancyDetailModel.AddressModel?): VacancyItem.AddressItem? =
        if (model != null) {
            VacancyItem.AddressItem(
                city = model.city,
                street = model.street,
                building = model.building,
                fullAddress = model.fullAddress
            )
        } else {
            null
        }

    private fun mapExperienceFromDomain(model: VacancyDetailModel.ExperienceModel?): VacancyItem.ExperienceItem? =
        if (model != null) {
            VacancyItem.ExperienceItem(
                id = model.id,
                name = model.name
            )
        } else {
            null
        }

    private fun mapScheduleFromDomain(model: VacancyDetailModel.ScheduleModel?): VacancyItem.ScheduleItem? =
        if (model != null) {
            VacancyItem.ScheduleItem(
                id = model.id,
                name = model.name
            )
        } else {
            null
        }

    private fun mapEmploymentFromDomain(model: VacancyDetailModel.EmploymentModel?): VacancyItem.EmploymentItem? =
        if (model != null) {
            VacancyItem.EmploymentItem(
                id = model.id,
                name = model.name
            )
        } else {
            null
        }

    private fun mapContactsFromDomain(model: VacancyDetailModel.ContactsModel?): VacancyItem.ContactsItem? =
        if (model != null) {
            VacancyItem.ContactsItem(
                id = model.id,
                name = model.name,
                email = model.email,
                phone = model.phones.map { it.formatted }
            )
        } else {
            null
        }

    private fun mapEmployerFromDomain(model: VacancyDetailModel.EmployerModel): VacancyItem.EmployerItem =
        VacancyItem.EmployerItem(
            id = model.id,
            name = model.name,
            logo = model.logo
        )
}
