package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.data.database.FilterLocalDataSource
import ru.practicum.android.diploma.data.database.VacancyDatabase
import ru.practicum.android.diploma.data.dto.NetworkMonitor
import ru.practicum.android.diploma.data.network.YPApiService
import ru.practicum.android.diploma.data.repository.DatabaseFavoriteRepositoryImpl
import ru.practicum.android.diploma.data.repository.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.database.FavoriteRepository

val dataModule = module {

    val okHttpClient = OkHttpClient.Builder()
        .build()

    single<YPApiService> {
        Retrofit.Builder()
            .baseUrl("http://155.212.163.151/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YPApiService::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), VacancyDatabase::class.java, "database.db")
            .build()
    }
    single<VacancyDao> {
        get<VacancyDatabase>().vacancyDao()
    }
    single { Gson() }

    single { NetworkMonitor(get()) }

    factory<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }

    factory<FavoriteRepository> {
        DatabaseFavoriteRepositoryImpl(get(), get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "filter_prefs",
            Context.MODE_PRIVATE
        )
    }

    single {
        FilterLocalDataSource(get())
    }
}
