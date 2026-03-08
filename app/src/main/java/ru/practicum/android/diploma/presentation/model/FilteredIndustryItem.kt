package ru.practicum.android.diploma.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilteredIndustryItem(
    val id: Int,
    val name: String,
    val isChecked: Boolean = false
) : Parcelable
