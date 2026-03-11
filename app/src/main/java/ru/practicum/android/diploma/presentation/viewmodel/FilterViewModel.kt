package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.database.FilterInteractor
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterViewModel(
    private val filterSharedPref: FilterInteractor,
) : ViewModel() {
    private val filterLiveData = MutableLiveData<VacancyFilterModel>()
    fun observeFilterState(): LiveData<VacancyFilterModel> = filterLiveData

    init {
        updateFilter()
    }

    fun updateFilter() {
        viewModelScope.launch {
            val filter = filterSharedPref.getFilter()
            filterLiveData.postValue(filter)
        }
    }

    fun changeSalary(value: Int?) {
        val current = filterLiveData.value ?: return
        filterLiveData.value = current.copy(salaryFrom = value)
    }

    fun changeIncludeWithoutSalary(value: Boolean) {
        val current = filterLiveData.value ?: return
        filterLiveData.value = current.copy(includeWithoutSalary = value)
    }

    fun clearIndustry() {
        val current = filterLiveData.value ?: return
        filterLiveData.value = current.copy(industry = null)
        saveFilter()
    }

    fun saveFilter() {
        val currentFilter = filterLiveData.value ?: return

        viewModelScope.launch {
            filterSharedPref.saveFilter(currentFilter)
        }
    }

    fun resetFilter() {
        viewModelScope.launch {
            filterSharedPref.clearFilter()
        }
        updateFilter()
    }

    fun isBtnsVisible(): Boolean {
        val currentFilter = filterLiveData.value ?: return false
        return currentFilter.includeWithoutSalary ||
            currentFilter.salaryFrom != null && currentFilter.salaryFrom > 0 ||
            currentFilter.industry != null && currentFilter.industry.id > 0 && currentFilter.industry.name.isNotEmpty()
    }
}
