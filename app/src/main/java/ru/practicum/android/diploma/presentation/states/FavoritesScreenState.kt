package ru.practicum.android.diploma.presentation.states

import ru.practicum.android.diploma.presentation.model.VacancyItem

sealed class FavoritesScreenState {
    object Loading: FavoritesScreenState()
    object EmptyFavorites: FavoritesScreenState()
    object DBError: FavoritesScreenState()

    data class Content(
        val content: List<VacancyItem>
    ) : FavoritesScreenState()
}
