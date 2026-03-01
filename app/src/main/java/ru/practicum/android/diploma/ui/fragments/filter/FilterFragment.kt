package ru.practicum.android.diploma.ui.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private var isOnlySalaryChecked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toBackArrowButton()
        initSalaryInput()

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
        binding.filterOnlySalary.setOnClickListener {
            binding.filterOnlySalary.isSelected =
                !binding.filterOnlySalary.isSelected
        }
    }

    private fun toBackArrowButton() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initSalaryInput() {
        val activeColor = requireContext().getColor(R.color.button_background)
        val defaultColor = requireContext().getColor(R.color.input_text)
        fun updateUi() {
            val hasFocus = binding.editTextboxSalary.hasFocus()
            val textNotEmpty = binding.editTextboxSalary.text?.isNotEmpty() == true
            binding.salaryTitle.setTextColor(
                if (hasFocus || textNotEmpty) activeColor else defaultColor
            )
            binding.iconClear.visibility =
                if (hasFocus && textNotEmpty) View.VISIBLE else View.GONE
        }

        binding.editTextboxSalary.setOnFocusChangeListener { _, _ ->
            updateUi()
        }

        binding.editTextboxSalary.doOnTextChanged { text, _, _, _ ->
            updateUi()
        }

        binding.iconClear.setOnClickListener {
            binding.editTextboxSalary.text?.clear()
        }
    }
}
