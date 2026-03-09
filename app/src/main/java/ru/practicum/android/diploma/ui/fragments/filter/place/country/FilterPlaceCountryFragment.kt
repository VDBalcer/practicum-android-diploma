package ru.practicum.android.diploma.ui.fragments.filter.place.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceCountryBinding
import ru.practicum.android.diploma.presentation.states.FilterPlaceState
import ru.practicum.android.diploma.presentation.viewmodel.FilterPlaceViewModel
import ru.practicum.android.diploma.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.ui.fragments.filter.FilterBaseFragment
import ru.practicum.android.diploma.ui.fragments.filter.place.FilterPlaceItemViewAdapter
import kotlin.getValue

class FilterPlaceCountryFragment : FilterBaseFragment() {
    private var _binding: FragmentFilterPlaceCountryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilterPlaceViewModel by viewModel()
    private val filterViewModel: FilterViewModel by activityViewModel()
    private var _placeCountryAdapter: FilterPlaceItemViewAdapter? = null
    private val placeCountryAdapter get() = _placeCountryAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterPlaceCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _placeCountryAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(R.string.filter_country_fragment_title)
        onInitAdapter()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.loadCountries()
    }

    private fun render(state: FilterPlaceState) {
        when (state) {
            is FilterPlaceState.ServerError -> showServerError()
            is FilterPlaceState.PlaceNotFound -> showEmpty()
            is FilterPlaceState.Loading -> showLoading()
            is FilterPlaceState.Content -> showContent(state)
        }
    }

    private fun showServerError() {
        binding.apply {
            progressBar.visibility = View.GONE
            countryRecyclerView.visibility = View.GONE
//            infoResult.visibility = View.GONE
//            placeholderImage.setImageResource(R.drawable.placeholder_server_error)
//            placeholderMessage.visibility = View.VISIBLE
//            placeholderMessage.text = getString(R.string.title_server_error)
        }
    }

    private fun showEmpty() {
        binding.apply {
            progressBar.visibility = View.GONE
            countryRecyclerView.visibility = View.GONE
//            infoResult.visibility = View.GONE
//            placeholderImage.setImageResource(R.drawable.placeholder_server_error)
//            placeholderMessage.visibility = View.VISIBLE
//            placeholderMessage.text = getString(R.string.title_server_error)
        }
    }

    private fun showLoading() {
        binding.apply {
            countryRecyclerView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
//        hideKeyboard()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(state: FilterPlaceState.Content) {
        placeCountryAdapter.setData(state.areas)
        placeCountryAdapter.notifyDataSetChanged()
        binding.apply {
            progressBar.visibility = View.GONE
            countryRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun onInitAdapter() {
        _placeCountryAdapter = FilterPlaceItemViewAdapter {  country ->
            filterViewModel.changeCountry(country)
            findNavController().popBackStack()
        }

        binding.countryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = placeCountryAdapter
        }
    }
}
