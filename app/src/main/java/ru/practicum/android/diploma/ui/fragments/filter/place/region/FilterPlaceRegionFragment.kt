package ru.practicum.android.diploma.ui.fragments.filter.place.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceRegionBinding
import ru.practicum.android.diploma.ui.fragments.filter.FilterBaseFragment

class FilterPlaceRegionFragment: FilterBaseFragment()  {
    private var _binding: FragmentFilterPlaceRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterPlaceRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(R.string.filter_region_fragment_title)
    }
}
