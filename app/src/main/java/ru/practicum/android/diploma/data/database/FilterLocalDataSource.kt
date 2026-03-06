package ru.practicum.android.diploma.data.database

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.FilterIndustryModel
import ru.practicum.android.diploma.domain.models.VacancyFilterModel

class FilterLocalDataSource(
    private val sharedPreferences: SharedPreferences,
) {

    fun saveFilter(filter: VacancyFilterModel) {
        sharedPreferences.edit()
            .putInt(KEY_SALARY_FROM, filter.salaryFrom ?: NO_SALARY)
            .putBoolean(KEY_INCLUDE_WITHOUT_SALARY, filter.includeWithoutSalary)
            .putInt(KEY_INDUSTRY_ID, filter.industry?.id ?: 0)
            .putString(KEY_INDUSTRY_NAME, filter.industry?.name)
            .apply()
    }

    fun getFilter(): VacancyFilterModel {
        val salary = sharedPreferences.getInt(KEY_SALARY_FROM, NO_SALARY)
        val industryId = sharedPreferences.getInt(KEY_INDUSTRY_ID, 0)
        return VacancyFilterModel(
            salaryFrom = if (salary == NO_SALARY) null else salary,
            includeWithoutSalary = sharedPreferences.getBoolean(
                KEY_INCLUDE_WITHOUT_SALARY,
                false
            ),
            industry = if (industryId == NO_INDUSTRY) null
            else FilterIndustryModel(
                id = sharedPreferences.getInt(KEY_INDUSTRY_ID, 0),
                name = sharedPreferences.getString(KEY_INDUSTRY_NAME, "") ?: ""
            )
        )
    }

    fun clearFilter() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_SALARY_FROM = "key_salary_from"
        private const val KEY_INCLUDE_WITHOUT_SALARY = "key_include_without_salary"
        private const val KEY_INDUSTRY_ID = "key_industry_id"
        private const val KEY_INDUSTRY_NAME = "key_industry_name"

        private const val NO_SALARY = -1
        private const val NO_INDUSTRY = 0
    }
}
