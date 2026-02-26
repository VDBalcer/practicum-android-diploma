package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.ApiInteractor

class FilterViewModel(
    private val apiInteractor: ApiInteractor,
) : ViewModel()
