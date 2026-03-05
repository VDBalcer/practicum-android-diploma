package ru.practicum.android.diploma.ui.fragments.filter

import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.R

abstract class FilterBaseFragment : Fragment() {
    private var _rootToolbar: MaterialToolbar? = null
    protected val rootToolbar get() = _rootToolbar!!

    protected fun initToolbar(
        titleRes: Int,
        showBackButton: Boolean = true,
        clearMenu: Boolean = true
    ) {
        _rootToolbar = (activity as RootActivity).rootBinding.rootToolbar

        rootToolbar.title = getString(titleRes)

        if (showBackButton) {
            rootToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            rootToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        } else {
            rootToolbar.navigationIcon = null
        }

        if (clearMenu) {
            rootToolbar.menu.forEach { it.isVisible = false }
        }
    }
}
