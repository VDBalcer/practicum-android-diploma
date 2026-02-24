package ru.practicum.android.diploma.ui.fragments.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.MainScreenState
import ru.practicum.android.diploma.presentation.FilterViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by viewModel()

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
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitListener()
//        binding.mainVacancyButton.setOnClickListener {
//            findNavController().navigate(
//                R.id.action_mainFragment_to_vacancyDetailsFragment
//            )
//        }
//
        binding.filter.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFragment_to_filterFragment
            )
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun onInitListener() {
        binding.editTextboxJobSearch.doOnTextChanged { text, _, _, _ ->
            val query = text?.toString() ?: ""

            updateIcons(query.isNotEmpty())

            viewModel.searchDebounce(query)
        }
        binding.iconClear.setOnClickListener {
            binding.editTextboxJobSearch.text?.clear()
            val ims = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            ims?.hideSoftInputFromWindow(binding.editTextboxJobSearch.windowToken, 0)
        }
    }
    private fun updateIcons(hasText: Boolean) {
        binding.iconClear.isVisible = hasText
        binding.iconSearch.isVisible = !hasText
    }
    private fun render(state: MainScreenState) {
        with(binding) {
            placeholderStartSearch.isVisible = false
            containerNotInternet.isVisible = false
            containerJobNotFound.isVisible = false
            progressBar.isVisible = false
            vacanciesRecyclerView.isVisible = false
            infoResult.isVisible = false
            when (state) {
                is MainScreenState.StartSearch -> placeholderStartSearch.isVisible = true
                is MainScreenState.NoInternet -> containerNotInternet.isVisible = true
                is MainScreenState.JobNotFound -> {
                    containerJobNotFound.isVisible = true
                    infoResult.isVisible = true
                }
                is MainScreenState.Loading -> progressBar.isVisible = true
                is MainScreenState.Content -> {
                    vacanciesRecyclerView.isVisible = true
                    infoResult.isVisible = true
                }
            }
        }
    }
}
