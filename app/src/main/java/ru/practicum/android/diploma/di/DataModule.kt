package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.NetworkMonitor

val dataModule = module {
    single { NetworkMonitor(get()) }
}
