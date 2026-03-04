package ru.practicum.android.diploma.ui.fragments.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.presentation.model.VacancyItem

class FavoritesItemViewAdapter(
    private val clickListener: FavoritesClickListener
) : RecyclerView.Adapter<FavoritesItemViewHolder>() {

    private val vacancyList = mutableListOf<VacancyItem>()

    fun setData(vacancies: List<VacancyItem>) {
        this.vacancyList.clear()
        this.vacancyList.addAll(vacancies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesItemViewHolder {
        return FavoritesItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FavoritesItemViewHolder,
        position: Int
    ) {
        holder.bind(vacancyList[position])

        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(vacancyList[position])
        }
    }

    override fun getItemCount(): Int = vacancyList.size

    fun interface FavoritesClickListener {
        fun onVacancyClick(vacancy: VacancyItem)
    }
}
