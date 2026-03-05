package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.ApiInteractor
import ru.practicum.android.diploma.domain.database.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.presentation.model.FilterState
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

class FilterViewModel(
    private val apiInteractor: ApiInteractor,
    private val filterSharedPref: FilterInteractor,
) : ViewModel() {
    private val filterLiveData = MutableLiveData<FilterState>()
    fun observeFilterState(): LiveData<FilterState> = filterLiveData


    init {
        viewModelScope.launch {
            val filter = filterSharedPref.getFilter()

            if (
                filter.salaryFrom != null ||
                filter.includeWithoutSalary ||
                filter.industry != null
            ) {
                filterLiveData.postValue(FilterState.EditedState(filter))
            } else {
                filterLiveData.postValue(FilterState.NotEditedState)
            }
        }
    }

    fun saveFilter() {
        val current = filterLiveData.value

        if (current is FilterState.EditedState) {
            viewModelScope.launch {
                filterSharedPref.saveFilter(
                    VacancyFilterModel(
                        salaryFrom = current.filter.salaryFrom,
                        includeWithoutSalary = current.filter.includeWithoutSalary,
                        industry = current.filter.industry
                    )
                )
            }
        }
    }
}
