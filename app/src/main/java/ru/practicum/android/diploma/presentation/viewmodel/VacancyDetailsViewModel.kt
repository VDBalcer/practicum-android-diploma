package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.database.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyDetailScreenState

class VacancyDetailsViewModel(
    handle: SavedStateHandle,
    private val apiInteractor: ApiInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val externalNavigator: ExternalNavigator,
) : ViewModel() {
    private val vacancyId: String =
        requireNotNull(handle["vacancyId"])

    private val vacancyDetailsStateLiveData =
        MutableLiveData<VacancyDetailScreenState>(VacancyDetailScreenState.Loading)

    fun observeMainState(): LiveData<VacancyDetailScreenState> = vacancyDetailsStateLiveData

    init {
        viewModelScope.launch {
            val isFavorite = favoriteInteractor
                .isVacancyInFavorite(vacancyId)
                .first()
            when (val result: NetworkResult<VacancyDetailModel> = apiInteractor.getVacancy(vacancyId)) {
                is NetworkResult.Error -> {
                    if (isFavorite) favoriteInteractor.deleteFromFavorite(vacancyId)
                    vacancyDetailsStateLiveData
                        .postValue(
                            VacancyDetailScreenState.JobNotFound
                        )
                }

                is NetworkResult.NetworkError -> {
                    viewModelScope.launch {
                        favoriteInteractor
                            .getFavoriteVacancyById(vacancyId)
                            .collect { cachedVacancy ->
                                if (cachedVacancy != null) {
                                    vacancyDetailsStateLiveData.postValue(
                                        VacancyDetailScreenState.Content(cachedVacancy, true)
                                    )
                                } else {
                                    vacancyDetailsStateLiveData.postValue(
                                        VacancyDetailScreenState.ServerError
                                    )
                                }
                            }
                    }
                }

                is NetworkResult.Success<VacancyDetailModel> -> {
                    vacancyDetailsStateLiveData
                        .postValue(
                            VacancyDetailScreenState.Content(
                                result.data,
                                isFavorite
                            )
                        )
                }

            }
        }
    }

    fun onEmailClicked(email: String) {
        externalNavigator.sendEmail(email)
    }

    fun onCallClicked(phone: String) {
        externalNavigator.callPhone(phone)
    }

    fun onShareClicked(text: String) {
        externalNavigator.shareLink(text)
    }

    fun onFavoriteClick() {
        val currentState = vacancyDetailsStateLiveData.value

        if (currentState is VacancyDetailScreenState.Content) {
            viewModelScope.launch {
                if (currentState.isFavorite) {
                    favoriteInteractor.deleteFromFavorite(currentState.vacancy.id)
                } else {
                    favoriteInteractor.addToFavorite(currentState.vacancy)
                }
            }
        }
    }
}
