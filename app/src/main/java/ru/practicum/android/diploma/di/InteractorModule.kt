package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.database.FavoriteInteractor
import ru.practicum.android.diploma.domain.impl.DatabaseFavoriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.YPApiInteractorImpl

val interactorModule = module {
    factory<ApiInteractor> {
        YPApiInteractorImpl(get())
    }

    single<FavoriteInteractor> {
        DatabaseFavoriteInteractorImpl(get())
    }
}
