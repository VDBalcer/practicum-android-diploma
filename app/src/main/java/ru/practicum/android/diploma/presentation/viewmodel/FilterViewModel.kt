package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.database.FilterInteractor

class FilterViewModel(
    private val apiInteractor: ApiInteractor,
    private val filterSharedPref: FilterInteractor,
) : ViewModel()
