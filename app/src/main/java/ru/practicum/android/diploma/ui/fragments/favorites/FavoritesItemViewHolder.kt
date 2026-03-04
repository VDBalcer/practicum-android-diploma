package ru.practicum.android.diploma.ui.fragments.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemFavoriteBinding
import ru.practicum.android.diploma.presentation.model.VacancyItem
import ru.practicum.android.diploma.util.Converter
import ru.practicum.android.diploma.util.formatSalary

class FavoritesItemViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

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
            .into(binding.favoritesImageLogo)

        binding.apply {
            favoritesVacancyName.text = binding.root.resources.getString(
                R.string.vacancy_name_and_address,
                item.name,
                item.area.name
            )
            favoritesCompanyName.text = item.employer.name
            favoritesWages.text = formatSalary(
                from = item.salary?.from,
                to = item.salary?.to,
                currency = item.salary?.currency
            )
        }
    }

    companion object {
        fun from(parent: ViewGroup): FavoritesItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
            return FavoritesItemViewHolder(binding)
        }
    }
}
