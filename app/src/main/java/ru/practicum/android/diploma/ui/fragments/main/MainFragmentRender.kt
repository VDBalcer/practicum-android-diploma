package ru.practicum.android.diploma.ui.fragments.main

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.events.ErrorType
import ru.practicum.android.diploma.presentation.events.MainScreenEvent
import ru.practicum.android.diploma.presentation.states.MainScreenState

internal fun MainFragment.render(state: MainScreenState) {
    when (state) {
        is MainScreenState.StartSearch -> showStart()
        is MainScreenState.NoInternet -> showNotInternetPlaceholder()
        is MainScreenState.ServerError -> showServerError()
        is MainScreenState.JobNotFound -> showEmpty()
        is MainScreenState.Loading -> showLoading()
        is MainScreenState.Content -> showContent(state)
    }
}

private fun MainFragment.showStart() {
    binding.apply {
        containerPlaceholder.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        vacanciesRecyclerView.visibility = View.GONE
        infoResult.visibility = View.GONE
        placeholderImage.setImageResource(R.drawable.placeholder_start_search)
        placeholderMessage.visibility = View.GONE
    }
}

private fun MainFragment.showLoading() {
    binding.apply {
        containerPlaceholder.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        vacanciesRecyclerView.visibility = View.GONE
        infoResult.visibility = View.GONE
    }
    val ims = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
    ims?.hideSoftInputFromWindow(binding.editTextboxJobSearch.windowToken, 0)
}

@SuppressLint("NotifyDataSetChanged")
private fun MainFragment.showContent(content: MainScreenState.Content) {
    binding.apply {
        containerPlaceholder.visibility = View.GONE
        progressBar.visibility = View.GONE
        vacanciesRecyclerView.visibility = View.VISIBLE
        infoResult.visibility = View.VISIBLE
        progressBarPagination.isVisible = content.isPaginationLoading
        infoResult.text = resources.getQuantityString(
            R.plurals.vacancies_found, content.response.found, content.response.found
        )
        vacancyAdapter.setData(content.response.vacancies)
        vacancyAdapter.notifyDataSetChanged()
    }
}

private fun MainFragment.showEmpty() {
    binding.apply {
        containerPlaceholder.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        vacanciesRecyclerView.visibility = View.GONE
        infoResult.visibility = View.VISIBLE
        infoResult.text = getString(R.string.result_not_found)
        placeholderImage.setImageResource(R.drawable.placeholder_nothing_found)
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = getString(R.string.title_vacancy_not_found)
    }
}

private fun MainFragment.showNotInternetPlaceholder() {
    binding.apply {
        containerPlaceholder.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        vacanciesRecyclerView.visibility = View.GONE
        infoResult.visibility = View.GONE

        placeholderImage.setImageResource(R.drawable.placeholder_not_internet)
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = getString(R.string.title_not_internet)
    }
}

private fun MainFragment.showServerError() {
    binding.apply {
        containerPlaceholder.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        vacanciesRecyclerView.visibility = View.GONE
        infoResult.visibility = View.GONE
        placeholderImage.setImageResource(R.drawable.placeholder_server_error)
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = getString(R.string.title_server_error)
    }
}

internal fun MainFragment.onInitPaginationErrorHandler() {
    lifecycleScope.launchWhenStarted {
        viewModel.events.collect { event ->
            when (event) {
                is MainScreenEvent.ShowError -> {
                    val message = when (event.type) {
                        ErrorType.NETWORK -> getString(R.string.network_error_toast_text)

                        ErrorType.NO_INTERNET -> getString(R.string.no_internet_error_toast_text)
                    }

                    Toast.makeText(
                        requireContext(),
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
