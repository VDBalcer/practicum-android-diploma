package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.presentation.viewmodel.MainFragmentViewModel
import ru.practicum.android.diploma.presentation.FilterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel {
        FilterViewModel(get())
    }

    viewModel {
        MainFragmentViewModel(
            get(),
            get()
        )
    }
}
