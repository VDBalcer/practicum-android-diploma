package ru.practicum.android.diploma.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VacancyItem(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryItem?,
    val address: AddressItem?,
    val experience: ExperienceItem?,
    val schedule: ScheduleItem?,
    val employment: EmploymentItem?,
    val contacts: ContactsItem?,
    val employer: EmployerItem,
    val area: FilteredAreaItem,
    val skills: List<String>,
    val url: String,
    val industry: FilteredIndustryItem,
) : Parcelable {

    @Parcelize
    data class SalaryItem(
        val from: Int?,
        val to: Int?,
        val currency: String?,
    ) : Parcelable

    @Parcelize
    data class AddressItem(
        val city: String,
        val street: String,
        val building: String,
        val fullAddress: String,
    ) : Parcelable

    @Parcelize
    data class ExperienceItem(
        val id: String,
        val name: String,
    ) : Parcelable

    @Parcelize
    data class ScheduleItem(
        val id: String,
        val name: String,
    ) : Parcelable

    @Parcelize
    data class EmploymentItem(
        val id: String,
        val name: String,
    ) : Parcelable

    @Parcelize
    data class ContactsItem(
        val id: String,
        val name: String,
        val email: String,
        val phone: List<String>,
    ) : Parcelable

    @Parcelize
    data class EmployerItem(
        val id: String,
        val name: String,
        val logo: String,
    ) : Parcelable
}
