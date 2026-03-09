package ru.practicum.android.diploma.ui.fragments.filter.place

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.presentation.model.FilteredAreaItem

class FilterPlaceItemViewAdapter(
    private val clickListener: FilterPlaceClickListener
) : RecyclerView.Adapter<FilterPlaceItemViewHolder>() {
    private val areaList = mutableListOf<FilteredAreaItem>()

    fun setData(areas: List<FilteredAreaItem>) {
        this.areaList.clear()
        this.areaList.addAll(areas)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterPlaceItemViewHolder {
        return FilterPlaceItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FilterPlaceItemViewHolder,
        position: Int
    ) {
        holder.bind(areaList[position])
        holder.itemView.setOnClickListener {
            clickListener.onAreaClick(areaList[position])
        }
    }

    override fun getItemCount(): Int = areaList.size

    fun interface FilterPlaceClickListener {
        fun onAreaClick(areaItem: FilteredAreaItem)
    }
}
