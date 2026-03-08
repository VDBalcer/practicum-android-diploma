package ru.practicum.android.diploma.ui.fragments.filter.field

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemFilterFieldBinding
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

class FilterIndustryItemViewHolder(private val binding: ItemFilterFieldBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(field: FilteredIndustryItem) {
        binding.filterFieldName.text = field.name
        binding.filterFieldName.isChecked = field.isChecked
    }

    companion object{
        fun from(parent: ViewGroup): FilterIndustryItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemFilterFieldBinding.inflate(inflater, parent, false)
            return FilterIndustryItemViewHolder(binding)
        }
    }
}
