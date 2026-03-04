package ru.practicum.android.diploma.ui.fragments.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.presentation.model.VacancyItem

class VacancyItemViewAdapter(
    private val clickListener: VacancyClickListener
) : RecyclerView.Adapter<VacancyItemViewHolder>() {

    private val vacancyList = mutableListOf<VacancyItem>()

    fun setData(vacancies: List<VacancyItem>) {
        this.vacancyList.clear()
        this.vacancyList.addAll(vacancies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VacancyItemViewHolder {
        return VacancyItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: VacancyItemViewHolder,
        position: Int
    ) {
        holder.bind(vacancyList[position])

        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(vacancyList[position])
        }
    }

    override fun getItemCount(): Int = vacancyList.size

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: VacancyItem)
    }
}
