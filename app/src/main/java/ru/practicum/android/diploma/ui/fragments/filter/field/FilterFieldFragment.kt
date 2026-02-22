package ru.practicum.android.diploma.ui.fragments.filter.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentFilterFieldBinding

class FilterFieldFragment : Fragment() {
    private var _binding: FragmentFilterFieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterFieldBinding.inflate(inflater, container, false)
        return binding.root
    }
}
