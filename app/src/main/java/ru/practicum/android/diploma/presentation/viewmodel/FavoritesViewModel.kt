package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.database.FavoriteInteractor
import ru.practicum.android.diploma.presentation.mapper.toItem
import ru.practicum.android.diploma.presentation.model.VacancyItem
import ru.practicum.android.diploma.presentation.states.FavoritesScreenState

class FavoritesViewModel(
    private val interactor: FavoriteInteractor
) : ViewModel() {
    private val favoritesStateLiveData = MutableLiveData<FavoritesScreenState>(
        FavoritesScreenState.Loading
    )

    fun observeFavoriteState(): LiveData<FavoritesScreenState> = favoritesStateLiveData

    fun showFavorites() {
        favoritesStateLiveData.postValue(FavoritesScreenState.Loading)

        viewModelScope.launch {
            try {
                interactor
                    .getAllFavoritesVacancy()
                    .collect { response ->
                        processResult(
                            response.map { it.toItem() }
                                .reversed()
                        )
                    }
            } catch (_: RuntimeException) {
                processResult(null)
            }
        }
    }

    private fun processResult(response: List<VacancyItem>?) {
        val state = if (response != null) {
            if (response.isEmpty()) {
                FavoritesScreenState.EmptyFavorites
            } else {
                FavoritesScreenState.Content(response)
            }
        } else {
            FavoritesScreenState.DBError
        }

        favoritesStateLiveData.postValue(state)
    }
}
