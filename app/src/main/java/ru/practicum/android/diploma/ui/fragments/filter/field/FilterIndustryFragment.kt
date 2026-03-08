package ru.practicum.android.diploma.ui.fragments.filter.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterFieldBinding
import ru.practicum.android.diploma.ui.fragments.filter.FilterBaseFragment

class FilterIndustryFragment : FilterBaseFragment() {
    private var _binding: FragmentFilterFieldBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun initAdapter() {
        binding.filterFieldRecycler.layoutManager = LinearLayoutManager(requireContext())
        _industryAdapter = FilterIndustryItemViewAdapter { }
        binding.filterFieldRecycler.adapter = industryAdapter
    }
}
