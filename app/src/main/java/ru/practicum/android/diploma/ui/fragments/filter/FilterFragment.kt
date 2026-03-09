package ru.practicum.android.diploma.ui.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.util.renderAreaItem

class FilterFragment : FilterBaseFragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveFilter()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSalaryInput()
        initToolbar(R.string.filter_fragment_title)
        initFilterObserver()
        binding.filterAreaItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterFragment_to_filterPlaceFragment
            )
        }

        binding.filterIndustryItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterFragment_to_filterFieldFragment
            )
        }

        binding.btnApply.setOnClickListener {
            viewModel.saveFilter()
            parentFragmentManager.setFragmentResult(
                "filter_result",
                bundleOf("apply_filter" to true)
            )
            findNavController().popBackStack()
        }
        binding.btnReset.setOnClickListener {
            view.findFocus()?.clearFocus()
            viewModel.resetFilter()
        }
    }

    private fun initSalaryInput() {
        binding.salaryInputLayout.isEndIconVisible = false
        binding.salaryInputLayout.setEndIconOnClickListener {
            val editText = binding.salaryInputEditText
            editText.text?.clear()
            val imm = requireContext()
                .getSystemService(InputMethodManager::class.java)
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            editText.clearFocus()
        }
        binding.salaryInputEditText.doAfterTextChanged { text ->
            binding.salaryInputLayout.isEndIconVisible = !text.isNullOrEmpty()

            val salary = text?.toString()?.toIntOrNull()
            viewModel.changeSalary(salary)

        }
    }

    private fun initFilterObserver() {
        viewModel.observeFilterState().observe(viewLifecycleOwner) { filter ->
            with(binding) {
                filterOnlySalary.setOnCheckedChangeListener(null)
                filterOnlySalary.isChecked = filter.includeWithoutSalary
                filterOnlySalary.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.changeIncludeWithoutSalary(isChecked)
                }
                if (!salaryInputEditText.isFocused) {
                    val text = filter.salaryFrom?.toString().orEmpty()
                    if (salaryInputEditText.text.toString() != text) {
                        salaryInputEditText.setText(text)
                    }
                }
                filter.industry?.name
                    ?.takeIf { it.isNotBlank() }
                    ?.let { name -> filterIndustryItem.text = name }
                val btnsVisibility = viewModel.isBtnsVisible()
                btnReset.isVisible = btnsVisibility
                btnApply.isVisible = btnsVisibility
                renderContent(filter)
            }
        }
    }

    private fun renderContent(filter: VacancyFilterModel) {
        val countryItem = filter.country?.let { country ->
            val name = if (filter.region != null) {
                "${country.name}, ${filter.region.name}"
            } else {
                country.name
            }
            country.copy(name = name)
        }
        renderAreaItem(
            item = countryItem,
            label = binding.filterAreaLabel,
            value = binding.filterAreaItem,
            arrow = binding.arrowArea,
            defaultText = R.string.filter_area_item
        )

        binding.btnApply.isVisible = filter.country != null
        binding.btnReset.isVisible = filter.country != null
    }
}
