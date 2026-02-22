package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.NetworkResult
import ru.practicum.android.diploma.data.repository.YPApiRepository
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    private val repository: YPApiRepository by inject()
    private val topLevelDestinations = setOf(
        R.id.main_fragment,
        R.id.favorites_fragment,
        R.id.team_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = if (binding.rootBottomNavMenu.isVisible) 0 else systemBars.bottom
            )

            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.root_fragment_container) as? NavHostFragment
            ?: error("NavHostFragment not found")
        val navController = navHostFragment.navController

        binding.rootBottomNavMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.rootBottomNavMenu.isVisible =
                destination.id in topLevelDestinations
            ViewCompat.requestApplyInsets(binding.root)
        }

        // Пример запроса к API
        networkRequestExample()
    }

    private fun networkRequestExample() {
        lifecycleScope.launch {
            val result = repository.getIndustries() // Любой тестируемый запрос

            when (result) {
                is NetworkResult.Success -> {
                    Log.d(NETWORK_DEBUG_TAG, "Success: ${result.data}")
                }

                is NetworkResult.Error -> {
                    Log.e(NETWORK_DEBUG_TAG, "HTTP error: ${result.code}")
                }

                is NetworkResult.NetworkError -> {
                    Log.e(NETWORK_DEBUG_TAG, "Network error")
                }
            }
        }
    }

    companion object {
        private const val NETWORK_DEBUG_TAG = "NETWORK_TEST"
    }

}
