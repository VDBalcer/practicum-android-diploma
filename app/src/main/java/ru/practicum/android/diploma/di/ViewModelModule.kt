package ru.practicum.android.diploma.di

import androidx.lifecycle.SavedStateHandle
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.presentation.viewmodel.MainFragmentViewModel
import ru.practicum.android.diploma.presentation.viewmodel.VacancyDetailsViewModel

val viewModelModule = module {
    viewModel {
        FilterViewModel(get())
    }

    viewModel {
        MainFragmentViewModel(
            get()
        )
    }
    viewModel { (handle: SavedStateHandle) ->
        VacancyDetailsViewModel(
            handle,
            get(),
            get(),
            get()
        )
    }
}
