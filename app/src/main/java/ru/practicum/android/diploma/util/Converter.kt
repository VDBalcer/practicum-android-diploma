package ru.practicum.android.diploma.util

import android.util.TypedValue
import android.view.View

object Converter {
    fun dpToPx(value: Float, targetView: View): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            targetView.resources.displayMetrics
        ).toInt()
    }
}
