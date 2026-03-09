package ru.practicum.android.diploma.util

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

fun renderAreaItem(
    item: FilteredAreaItem?,
    label: TextView,
    value: TextView,
    arrow: ImageView,
    @StringRes defaultText: Int
) {
    val context = value.context

    if (item != null) {
        label.isVisible = true
        value.text = item.name
        value.setTextColor(context.getColor(R.color.input_text))
        arrow.setImageResource(R.drawable.icon_close)
    } else {
        label.isVisible = false
        value.setText(defaultText)
        value.setTextColor(context.getColor(R.color.filter_item_light_font))
        arrow.setImageResource(R.drawable.arrow_forward)
    }
}
