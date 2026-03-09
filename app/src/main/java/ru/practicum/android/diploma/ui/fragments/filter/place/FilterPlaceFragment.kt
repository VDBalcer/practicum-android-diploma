package ru.practicum.android.diploma.ui.fragments.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceBinding
import ru.practicum.android.diploma.domain.models.VacancyFilterModel
import ru.practicum.android.diploma.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.ui.fragments.filter.FilterBaseFragment
import ru.practicum.android.diploma.util.renderAreaItem

class FilterPlaceFragment : FilterBaseFragment() {
    private var _binding: FragmentFilterPlaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by activityViewModel ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        initToolbar(R.string.filter_place_fragment_title)

        viewModel.observeFilterState().observe(viewLifecycleOwner) { filter ->
            renderContent(filter)
        }

        binding.filterAreaCountryItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceFragment_to_filterPlaceCountryFragment
            )
        }
        binding.filterAreaRegionItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceFragment_to_filterPlaceRegionFragment
            )
        }

        binding.arrowCountry.setOnClickListener {
            val filter = viewModel.observeFilterState().value ?: return@setOnClickListener
            if (filter.country != null) {
                viewModel.clearCountry()
                viewModel.clearRegion()
                binding.btnApply.isVisible = false

            }
        }

        binding.arrowRegion.setOnClickListener {
            val filter = viewModel.observeFilterState().value ?: return@setOnClickListener
            if (filter.region != null) {
                viewModel.clearRegion()
            }
        }

        binding.btnApply.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderContent(filter: VacancyFilterModel) {
        renderAreaItem(
            item = filter.country,
            label = binding.filterCountryLabel,
            value = binding.filterAreaCountryItem,
            arrow = binding.arrowCountry,
            defaultText = R.string.filter_area_country_item
        )

        renderAreaItem(
            item = filter.region,
            label = binding.filterRegionLabel,
            value = binding.filterAreaRegionItem,
            arrow = binding.arrowRegion,
            defaultText = R.string.filter_area_region_item
        )
        binding.btnApply.isVisible = filter.country!=null
    }
}
