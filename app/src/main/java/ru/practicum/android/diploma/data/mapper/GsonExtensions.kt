package ru.practicum.android.diploma.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJsonOrNull(json: String?): T? {
    if (json.isNullOrBlank()) return null
    return fromJson(json, T::class.java)
}

inline fun <reified T> Gson.fromJsonListOrNull(json: String?): List<T> {
    val type = object : TypeToken<List<T>>() {}.type
    return fromJson(json, type)
}
