package ru.practicum.android.diploma.presentation.events

sealed class MainScreenEvent {
    data class ShowError(val type: ErrorType) : MainScreenEvent()
}
enum class ErrorType {
    NETWORK,
    NO_INTERNET
}
