package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer<T>(
    private val delayMillis: Long,
    private val scope: CoroutineScope,
    private val action: (T) -> Unit
) {

    private var job: Job? = null

    fun submit(param: T) {
        job?.cancel()

        job = scope.launch {
            delay(delayMillis)
            action(param)
        }
    }

    fun cancel() {
        job?.cancel()
    }
}
