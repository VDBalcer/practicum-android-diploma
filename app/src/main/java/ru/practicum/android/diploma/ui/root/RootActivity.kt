package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.NetworkResult
import ru.practicum.android.diploma.data.repository.YPApiRepository

class RootActivity : AppCompatActivity() {
    private val repository: YPApiRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        // Пример запроса к API
        networkRequestExample()
    }

    private fun networkRequestExample() {
        lifecycleScope.launch {

            val result = repository.getIndustries() // Любой тестируемый запрос

            when (result) {
                is NetworkResult.Success -> {
                    Log.d("NETWORK_TEST", "Success: ${result.data}")
                }
                is NetworkResult.Error -> {
                    Log.e("NETWORK_TEST", "HTTP error: ${result.code}")
                }
                NetworkResult.NetworkError -> {
                    Log.e("NETWORK_TEST", "Network error")
                }
            }
        }
    }

}
