package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.domain.api.ApiInteractor

class RootActivity : AppCompatActivity() {
    private var _binding: ActivityRootBinding? = null
    val rootBinding get() = _binding!!
    private val repository: ApiInteractor by inject()
    private val topLevelDestinations = setOf(
        R.id.main_fragment,
        R.id.favorites_fragment,
        R.id.team_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(rootBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(rootBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = if (this@RootActivity.rootBinding.rootBottomNavMenu.isVisible) 0 else systemBars.bottom
            )

            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.root_fragment_container) as? NavHostFragment
            ?: error("NavHostFragment not found")
        val navController = navHostFragment.navController

        rootBinding.rootBottomNavMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            this@RootActivity.rootBinding.rootBottomNavMenu.isVisible =
                destination.id in topLevelDestinations
            ViewCompat.requestApplyInsets(rootBinding.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
