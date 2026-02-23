package ru.practicum.android.diploma.domain.models

sealed class MainScreenState {
    object StartSearch : MainScreenState()
    object NoInternet : MainScreenState()
    object JobNotFound : MainScreenState()
    object Loading : MainScreenState()
    // Заглушка для вакансий
    data class Content(
        val items: List<Any>
    ) : MainScreenState()
}
