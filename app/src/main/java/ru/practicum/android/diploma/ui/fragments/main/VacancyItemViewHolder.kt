package ru.practicum.android.diploma.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.presentation.model.VacancyItem
import ru.practicum.android.diploma.util.Converter

class VacancyItemViewHolder(private val binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VacancyItem) {
        val companyLogoUrl = item.employer.logo

        Glide.with(binding.root.context)
            .load(companyLogoUrl)
            .placeholder(R.drawable.company_logo_placeholder)
            .error(R.drawable.company_logo_placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(
                    Converter.dpToPx(14.0f, binding.root)
                )
            )
            .into(binding.imageLogo)

        var salary = ""

        if (item.salary != null) {
            if (item.salary.from != null) {
                salary += binding.root.context.getString(R.string.salary_from, item.salary.from)
            }
            if (item.salary.to != null) {
                salary += binding.root.context.getString(R.string.salary_to, item.salary.to)
            }

            salary += when (item.salary.currency) {
                "RUB" -> binding.root.context.getString(R.string.currency_rub)
                "EUR" -> binding.root.context.getString(R.string.currency_euro)
                "USD" -> binding.root.context.getString(R.string.currency_usd)
                else -> item.salary.currency
            }

            if (item.salary.from == null && item.salary.to == null) {
                salary = binding.root.context.getString(R.string.salary_not_specify)
            }
        } else {
            salary = binding.root.context.getString(R.string.salary_not_specify)
        }

        binding.apply {
            vacancyName.text = item.name
            companyName.text = item.employer.name
            wages.text = salary
        }
    }

    companion object {
        fun from(parent: ViewGroup): VacancyItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVacancyBinding.inflate(inflater, parent, false)
            return VacancyItemViewHolder(binding)
        }
    }
}
