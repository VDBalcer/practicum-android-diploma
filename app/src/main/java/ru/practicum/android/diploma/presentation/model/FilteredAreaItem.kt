package ru.practicum.android.diploma.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilteredAreaItem(
    val id: Int,
    val name: String,
    val parentId: Int,
    val areas: List<FilteredAreaItem>,
) : Parcelable
