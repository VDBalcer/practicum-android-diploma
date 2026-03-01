package ru.practicum.android.diploma.ui.fragments.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.model.VacancyItem
import ru.practicum.android.diploma.presentation.states.FavoritesScreenState
import ru.practicum.android.diploma.presentation.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.fragments.details.VacancyDetailsFragment

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    private var _favoritesAdapter: FavoritesItemViewAdapter? = null
    private val favoritesAdapter get() = _favoritesAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _favoritesAdapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeFavoriteState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        _favoritesAdapter = FavoritesItemViewAdapter { item ->
            findNavController().navigate(
                R.id.action_favoritesFragment_to_vacancyDetailsFragment,
                VacancyDetailsFragment.createArgs(item.id)
            )
        }
        binding.favoritesRecyclerView.adapter = favoritesAdapter

        viewModel.showFavorites()
    }

    private fun render(state: FavoritesScreenState) {
        when (state) {
            is FavoritesScreenState.Content -> showContent(state.content)
            FavoritesScreenState.DBError -> showError()
            FavoritesScreenState.EmptyFavorites -> showEmpty()
            FavoritesScreenState.Loading -> showLoading()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(content: List<VacancyItem>) {
        binding.apply {
            favoritesProgressbar.visibility = View.GONE
            favoritesRecyclerView.visibility = View.VISIBLE
            favoritesContainerPlaceholder.visibility = View.GONE

            favoritesAdapter.setData(content)
            favoritesAdapter.notifyDataSetChanged()
        }
    }

    private fun showLoading() {
        binding.apply {
            favoritesProgressbar.visibility = View.VISIBLE
            favoritesRecyclerView.visibility = View.GONE
            favoritesContainerPlaceholder.visibility = View.GONE
        }
    }

    private fun showError() {
        binding.apply {
            favoritesProgressbar.visibility = View.GONE
            favoritesRecyclerView.visibility = View.GONE
            favoritesContainerPlaceholder.visibility = View.VISIBLE

            favoritesPlaceholderImage.setImageResource(R.drawable.placeholder_nothing_found)
            favoritesPlaceholderMessage.text = getString(R.string.title_vacancy_not_found)
        }
    }

    private fun showEmpty() {
        binding.apply {
            favoritesProgressbar.visibility = View.GONE
            favoritesRecyclerView.visibility = View.GONE
            favoritesContainerPlaceholder.visibility = View.VISIBLE

            favoritesPlaceholderImage.setImageResource(R.drawable.placeholder_empty_list)
            favoritesPlaceholderMessage.text = getString(R.string.title_empty_list)
        }
    }
}
