package ru.practicum.android.diploma.ui.fragments.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.presentation.viewmodel.MainFragmentViewModel
import ru.practicum.android.diploma.ui.fragments.details.VacancyDetailsFragment
import ru.practicum.android.diploma.ui.root.RootActivity

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    internal val binding get() = _binding!!
    internal val viewModel: MainFragmentViewModel by viewModel()
    private var _vacancyAdapter: VacancyItemViewAdapter? = null
    internal val vacancyAdapter get() = _vacancyAdapter!!

    private var _rootToolbar: MaterialToolbar? = null
    private val rootToolbar get() = _rootToolbar!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vacancyAdapter = null
        _rootToolbar = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeMainSate().observe(viewLifecycleOwner) {
            render(it)
        }
        onInitToolbar()
        onInitListener()
        onInitAdapter()
        onInitPaginationErrorHandler()

        parentFragmentManager.setFragmentResultListener(
            "filter_result",
            viewLifecycleOwner
        ) { _, bundle ->

            val shouldApply = bundle.getBoolean("apply_filter")
            val searchText = binding.editTextboxJobSearch.text
            if (shouldApply && searchText.isNotEmpty()) {
                viewModel.searchDebounce(searchText?.toString() ?: "", true)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Обновление фильтра при возврате на страницу
        updateFilterState()
    }

    private fun updateFilterState() {
        val icon = if (viewModel.isFilterEdited()) {
            R.drawable.ic_filter_enabled
        } else {
            R.drawable.ic_filter_24
        }
        rootToolbar.menu.findItem(R.id.action_filter).setIcon(icon)
    }

    private fun onInitToolbar() {
        _rootToolbar = (activity as RootActivity).rootBinding.rootToolbar
        rootToolbar.title = getString(R.string.main_fragment_title)
        rootToolbar.navigationIcon = null
        rootToolbar.menu.forEach { it.isVisible = false }
        rootToolbar.menu.findItem(R.id.action_filter).isVisible = true

        rootToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_filter -> {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_filterFragment
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun onInitAdapter() {
        binding.vacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        _vacancyAdapter = VacancyItemViewAdapter { vacancy ->
            findNavController().navigate(
                R.id.action_mainFragment_to_vacancyDetailsFragment, VacancyDetailsFragment.createArgs(vacancy.id)
            )
        }
        binding.vacanciesRecyclerView.adapter = vacancyAdapter

        binding.vacanciesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = binding.vacanciesRecyclerView.layoutManager as? LinearLayoutManager ?: return
                    val pos = layoutManager.findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached(binding.editTextboxJobSearch.text.toString())
                    }
                }
            }
        })
    }

    private fun onInitListener() {
        binding.editTextboxJobSearch.doOnTextChanged { text, _, _, _ ->
            updateIcons(!text.isNullOrEmpty())
            viewModel.searchDebounce(text?.toString() ?: "")
        }
        binding.editTextboxJobSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchDebounce(binding.editTextboxJobSearch.text?.toString() ?: "")
                true
            } else {
                false
            }
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
}
