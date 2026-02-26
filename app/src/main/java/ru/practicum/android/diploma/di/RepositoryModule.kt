package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.YPApiRepositoryImpl
import ru.practicum.android.diploma.domain.api.ApiRepository

val repositoryModule = module {

    factory<ApiRepository> {
        YPApiRepositoryImpl(get(), get())
    }
}
