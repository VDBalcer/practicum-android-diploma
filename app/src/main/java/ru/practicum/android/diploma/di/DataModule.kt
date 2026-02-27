package ru.practicum.android.diploma.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.data.database.VacancyDatabase
import ru.practicum.android.diploma.data.dto.NetworkMonitor
import ru.practicum.android.diploma.data.network.YPApiService
import ru.practicum.android.diploma.data.repository.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.api.ExternalNavigator

val dataModule = module {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.API_ACCESS_TOKEN}"
                )
                .build()
            chain.proceed(request)
        }
        .build()

    single<YPApiService> {
        Retrofit.Builder()
            .baseUrl("https://practicum-diploma-8bc38133faba.herokuapp.com/")
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
    single { NetworkMonitor(get()) }

    factory<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}
