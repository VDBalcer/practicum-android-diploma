package ru.practicum.android.diploma.ui.fragments.filter.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemAreaBinding
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

class FilterPlaceItemViewHolder(private val binding: ItemAreaBinding) : RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: FilteredAreaItem) {
        binding.apply {
            filterAreaItem.text = item.name
        }
    }

    companion object {
        fun from(parent: ViewGroup): FilterPlaceItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAreaBinding.inflate(inflater, parent, false)
            return FilterPlaceItemViewHolder(binding)
        }
    }
}
