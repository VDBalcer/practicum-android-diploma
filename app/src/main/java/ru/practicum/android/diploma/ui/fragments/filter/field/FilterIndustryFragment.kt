package ru.practicum.android.diploma.ui.fragments.filter.field

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterFieldBinding
import ru.practicum.android.diploma.presentation.states.IndustryScreenState
import ru.practicum.android.diploma.presentation.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.ui.fragments.filter.FilterBaseFragment

class FilterIndustryFragment : FilterBaseFragment() {
    private var _binding: FragmentFilterFieldBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilterIndustryViewModel by viewModel()

    private var _industryAdapter: FilterIndustryItemViewAdapter? = null
    private val industryAdapter get() = _industryAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _industryAdapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(R.string.filter_field_title)
        initAdapter()
        initListeners()

        viewModel.observeIndustryState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initAdapter() {
        binding.filterFieldRecycler.layoutManager = LinearLayoutManager(requireContext())
        _industryAdapter = FilterIndustryItemViewAdapter {

        }
        binding.filterFieldRecycler.adapter = industryAdapter
    }

    private fun initListeners() {
        binding.editTextboxFieldSearch.doOnTextChanged { s, _, _, _ ->
            val industryFilterQuery = s?.toString() ?: ""
            updateIcon(industryFilterQuery.isNotBlank())
            viewModel.searchIndustry(industryFilterQuery)
        }

        binding.filterFieldIconClear.setOnClickListener {
            binding.editTextboxFieldSearch.text?.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val ims = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        ims?.hideSoftInputFromWindow(binding.editTextboxFieldSearch.windowToken, 0)
    }

    private fun updateIcon(hasText: Boolean) {
        binding.apply {
            filterFieldIconClear.isVisible = hasText
            filterFieldIconSearch.isVisible = !hasText
        }
    }

    private fun render(state: IndustryScreenState) {
        when (state) {
            is IndustryScreenState.Content -> showContent(state)
            IndustryScreenState.Loading -> showLoading()
            IndustryScreenState.NoInternet -> showNotInternetPlaceholder()
            IndustryScreenState.ServerError -> showErrorPlaceholder()
        }
    }

    private fun showLoading() {
        binding.apply {
            filterFieldProgressBar.visibility = View.VISIBLE
            filterFieldPlaceholder.visibility = View.GONE
            filterFieldContentContainer.visibility = View.GONE
        }
    }

    private fun showErrorPlaceholder() {
        binding.apply {
            filterFieldProgressBar.visibility = View.GONE
            filterFieldPlaceholder.visibility = View.VISIBLE
            filterFieldContentContainer.visibility = View.GONE

            filterFieldPlaceholderImage.setImageResource(R.drawable.placeholder_server_error)
            filterFieldPlaceholderMessage.text = getString(R.string.title_server_error)
        }
    }

    private fun showNotInternetPlaceholder() {
        binding.apply {
            filterFieldProgressBar.visibility = View.GONE
            filterFieldPlaceholder.visibility = View.VISIBLE
            filterFieldContentContainer.visibility = View.GONE

            filterFieldPlaceholderImage.setImageResource(R.drawable.placeholder_not_internet)
            filterFieldPlaceholderMessage.text = getString(R.string.title_not_internet)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(state: IndustryScreenState.Content) {
        binding.apply {
            filterFieldProgressBar.visibility = View.GONE
            filterFieldPlaceholder.visibility = View.GONE
            filterFieldContentContainer.visibility = View.VISIBLE
            filterFieldButtonChoose.isVisible = state.isIndustryReSelected

            industryAdapter.setData(state.industries)
            industryAdapter.notifyDataSetChanged()
        }
    }
}
