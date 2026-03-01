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
import ru.practicum.android.diploma.util.SalaryFormater

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
                    Converter.dpToPx(
                        binding.root.resources.getInteger(
                            R.integer.info_company_radius_int
                        ).toFloat(),
                        binding.root
                    )
                )
            )
            .into(binding.imageLogo)

        val salary = SalaryFormater.formatSalary(
            from = item.salary?.from,
            to = item.salary?.to,
            currency = item.salary?.currency
        )

        binding.apply {
            vacancyName.text = binding.root.resources.getString(
                R.string.vacancy_name_and_address,
                item.name,
                item.address?.city ?: ""
            )
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
