package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.DI.dataModule
import ru.practicum.android.diploma.DI.interactorModule
import ru.practicum.android.diploma.DI.repositoryModule
import ru.practicum.android.diploma.DI.viewModelModule

class PracticumApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // инициализация библиотек
        startKoin {
            androidContext(this@PracticumApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}
