package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.DatabaseFavoriteRepositoryImpl
import ru.practicum.android.diploma.data.repository.YPApiRepositoryImpl
import ru.practicum.android.diploma.domain.api.ApiRepository
import ru.practicum.android.diploma.domain.database.FavoriteRepository

val repositoryModule = module {

    factory<ApiRepository> {
        YPApiRepositoryImpl(get(), get())
    }

    single<FavoriteRepository> {
        DatabaseFavoriteRepositoryImpl(get(), get())
    }
}
