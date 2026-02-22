package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
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
    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!
    private val repository: YPApiRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val paddingBottom = if (binding.rootBottomNavMenu.isVisible) {
                0
            } else {
                systemBars.bottom
            }
            v.setPadding(systemBars.left, systemBars.top, systemBars.left, paddingBottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.root_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.rootBottomNavMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.rootBottomNavMenu.isVisible =
                destination.id == R.id.main_fragment || destination.id == R.id.favorites_fragment || destination.id == R.id.team_fragment
        }

        // Пример запроса к API
        networkRequestExample()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
