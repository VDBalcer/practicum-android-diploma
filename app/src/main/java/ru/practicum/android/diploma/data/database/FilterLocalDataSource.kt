package ru.practicum.android.diploma.data.database

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterLocalDataSource(
    private val sharedPreferences: SharedPreferences
) {

    fun saveFilter(filter: VacancyFilterModel) {
        sharedPreferences.edit()
            .putInt(KEY_SALARY_FROM, filter.salaryFrom ?: NO_SALARY)
            .putBoolean(KEY_INCLUDE_WITHOUT_SALARY, filter.includeWithoutSalary)
            .putString(KEY_INDUSTRY_ID, filter.industryId)
            .apply()
    }

    fun getFilter(): VacancyFilterModel {
        val salary = sharedPreferences.getInt(KEY_SALARY_FROM, NO_SALARY)

        return VacancyFilterModel(
            salaryFrom = if (salary == NO_SALARY) null else salary,
            includeWithoutSalary = sharedPreferences.getBoolean(
                KEY_INCLUDE_WITHOUT_SALARY,
                false
            ),
            industryId = sharedPreferences.getString(KEY_INDUSTRY_ID, null)
        )
    }

    fun clearFilter() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_SALARY_FROM = "key_salary_from"
        private const val KEY_INCLUDE_WITHOUT_SALARY = "key_include_without_salary"
        private const val KEY_INDUSTRY_ID = "key_industry_id"

        private const val NO_SALARY = -1
    }
}
