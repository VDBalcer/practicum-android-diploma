package ru.practicum.android.diploma.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.ui.root.RootActivity

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private var _rootToolbar: MaterialToolbar? = null
    private val rootToolbar get() = _rootToolbar!!

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
        _rootToolbar = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onInitToolbar()
    }

    private fun onInitToolbar() {
        _rootToolbar = (activity as RootActivity).rootBinding.rootToolbar
        rootToolbar.title = getString(R.string.favorites_fragment_title)
        rootToolbar.navigationIcon = null
        rootToolbar.menu.forEach { it.isVisible = false }
    }
}
