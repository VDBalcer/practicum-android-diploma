package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.YPApiService

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
}
