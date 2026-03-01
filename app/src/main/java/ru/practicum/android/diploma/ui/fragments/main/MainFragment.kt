package ru.practicum.android.diploma.ui.fragments.main

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.presentation.states.MainScreenState
import ru.practicum.android.diploma.presentation.viewmodel.MainFragmentViewModel
import ru.practicum.android.diploma.ui.fragments.details.VacancyDetailsFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModel()
    private var _vacancyAdapter: VacancyItemViewAdapter? = null
    private val vacancyAdapter get() = _vacancyAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vacancyAdapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeMainSate().observe(viewLifecycleOwner) {
            render(it)
        }
        onInitListener()
        onInitAdapter()
    }

    private fun onInitAdapter() {
        binding.vacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        _vacancyAdapter = VacancyItemViewAdapter { vacancy ->
            findNavController().navigate(
                R.id.action_mainFragment_to_vacancyDetailsFragment,
                VacancyDetailsFragment.createArgs(vacancy.id)
            )
        }
        binding.vacanciesRecyclerView.adapter = vacancyAdapter

        binding.vacanciesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager =
                        binding.vacanciesRecyclerView.layoutManager as? LinearLayoutManager
                            ?: return
                    val pos = layoutManager.findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
    }

    private fun onInitListener() {
        binding.editTextboxJobSearch.doOnTextChanged { text, _, _, _ ->
            updateIcons(!text.isNullOrEmpty())
            viewModel.searchDebounce(text?.toString() ?: "")
        }

        binding.iconClear.setOnClickListener {
            binding.editTextboxJobSearch.text?.clear()
            hideKeyboard()
        }

        binding.filter.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFragment_to_filterFragment
            )
        }
    }

    private fun hideKeyboard() {
        val ims = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        ims?.hideSoftInputFromWindow(binding.editTextboxJobSearch.windowToken, 0)
    }

    private fun updateIcons(hasText: Boolean) {
        binding.iconClear.isVisible = hasText
        binding.iconSearch.isVisible = !hasText
    }

    private fun render(state: MainScreenState) {
        when (state) {
            is MainScreenState.StartSearch -> showStart()
            is MainScreenState.NoInternet -> showNotInternetPlaceholder()
            is MainScreenState.ServerError -> showServerError()
            is MainScreenState.JobNotFound -> showEmpty()
            is MainScreenState.Loading -> showLoading()
            is MainScreenState.Content -> showContent(state)
        }
    }

    private fun showStart() {
        binding.apply {
            containerPlaceholder.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            vacanciesRecyclerView.visibility = View.GONE
            infoResult.visibility = View.GONE
            placeholderImage.setImageResource(R.drawable.placeholder_start_search)
            placeholderMessage.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            containerPlaceholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            vacanciesRecyclerView.visibility = View.GONE
            infoResult.visibility = View.GONE
        }
        hideKeyboard()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(content: MainScreenState.Content) {
        binding.apply {
            containerPlaceholder.visibility = View.GONE
            progressBar.visibility = View.GONE
            vacanciesRecyclerView.visibility = View.VISIBLE
            infoResult.visibility = View.VISIBLE
            progressBarPagination.isVisible = content.isPaginationLoading
            infoResult.text = resources.getQuantityString(
                R.plurals.vacancies_found,
                content.response.found,
                content.response.found
            )
            vacancyAdapter.setData(content.response.vacancies)
            vacancyAdapter.notifyDataSetChanged()
        }
    }

    private fun showEmpty() {
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

    private fun showNotInternetPlaceholder() {
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

    private fun showServerError() {
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
}
