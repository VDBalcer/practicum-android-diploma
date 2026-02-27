package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.api.NetworkResult
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyDetailScreenState

class VacancyDetailsViewModel(
    private val handle: SavedStateHandle,
    private val interactor: ApiInteractor,
    private val externalNavigator: ExternalNavigator
) : ViewModel() {
    private val vacancyId: String =
        requireNotNull(handle["vacancyId"])

    private val vacancyDetailsStateLiveData =
        MutableLiveData<VacancyDetailScreenState>(VacancyDetailScreenState.Loading)

    fun observeMainState(): LiveData<VacancyDetailScreenState> = vacancyDetailsStateLiveData

    init {
        viewModelScope.launch {
            when (val result: NetworkResult<VacancyDetailModel> = interactor.getVacancy(vacancyId)) {
                is NetworkResult.Error -> vacancyDetailsStateLiveData
                    .postValue(
                        VacancyDetailScreenState.JobNotFound
                    )

                is NetworkResult.NetworkError -> vacancyDetailsStateLiveData
                    .postValue(
                        VacancyDetailScreenState.ServerError
                    )

                is NetworkResult.Success<VacancyDetailModel> -> vacancyDetailsStateLiveData
                    .postValue(
                        VacancyDetailScreenState.Content(result.data)
                    )
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
}
