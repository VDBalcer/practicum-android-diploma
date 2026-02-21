package ru.practicum.android.diploma.ui.fragments.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceBinding

class FilterPlaceFragment : Fragment() {
    private var _binding: FragmentFilterPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterPlaceCountryButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceFragment_to_filterPlaceCountryFragment
            )
        }

        binding.filterPlaceRegionButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceFragment_to_filterPlaceRegionFragment
            )
        }
    }
}
