package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.YPApiRepository

val repositoryModule = module {

    single { YPApiRepository(get()) }
}
