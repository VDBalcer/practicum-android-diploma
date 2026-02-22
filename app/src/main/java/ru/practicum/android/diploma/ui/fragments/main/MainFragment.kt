package ru.practicum.android.diploma.ui.fragments.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun onInitListener() {
        binding.editTextboxJobSearch.doOnTextChanged { text, _, _, _ ->
            updateIcons(!text.isNullOrEmpty())
        }
        binding.iconClear.setOnClickListener {
            binding.editTextboxJobSearch.text?.clear()
            val ims = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            ims?.hideSoftInputFromWindow(binding.editTextboxJobSearch.windowToken, 0)
        }
    }

    private fun updateIcons(hasText: Boolean) = with(binding) {
        binding.iconClear.isVisible = hasText
        binding.iconSearch.isVisible = !hasText
    }
}
