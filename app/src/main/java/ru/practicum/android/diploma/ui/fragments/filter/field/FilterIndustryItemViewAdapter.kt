package ru.practicum.android.diploma.ui.fragments.filter.field

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.presentation.model.FilteredIndustryItem

class FilterIndustryItemViewAdapter(
    private val cLickListener: IndustryCLickListener
) : RecyclerView.Adapter<FilterIndustryItemViewHolder>() {

    private val industryList = mutableListOf<FilteredIndustryItem>()

    fun setData(industries: List<FilteredIndustryItem>) {
        this.industryList.clear()
        this.industryList.addAll(industries)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterIndustryItemViewHolder {
        return FilterIndustryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FilterIndustryItemViewHolder,
        position: Int
    ) {
        holder.bind(industryList[position])

        holder.itemView.setOnClickListener {
            cLickListener.onIndustryClick(industryList[position])
        }
    }

    override fun getItemCount(): Int = industryList.size

    fun interface IndustryCLickListener {
        fun onIndustryClick(industry: FilteredIndustryItem)
    }
}
