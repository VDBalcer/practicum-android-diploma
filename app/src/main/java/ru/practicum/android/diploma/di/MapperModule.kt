package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.mapper.FilteredAreaMapper
import ru.practicum.android.diploma.presentation.mapper.FilteredIndustryMapper
import ru.practicum.android.diploma.presentation.mapper.VacancyItemMapper
import ru.practicum.android.diploma.presentation.mapper.VacancyResponseItemMapper

val mapperModule = module() {
    single {
        VacancyResponseItemMapper(
            get()
        )
    }

    single {
        VacancyItemMapper(
            get(),
            get()
        )
    }

    single {
        FilteredAreaMapper()
    }

    single {
        FilteredIndustryMapper()
    }
}
