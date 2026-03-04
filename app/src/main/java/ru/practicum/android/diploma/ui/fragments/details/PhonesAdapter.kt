package ru.practicum.android.diploma.ui.fragments.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemPhoneBinding
import ru.practicum.android.diploma.domain.models.VacancyDetailModel

class PhonesAdapter(
    private val onPhoneClick: (String) -> Unit,
) : RecyclerView.Adapter<PhonesAdapter.PhoneViewHolder>() {

    private val items = mutableListOf<VacancyDetailModel.PhoneModel>()

    fun submitList(list: List<VacancyDetailModel.PhoneModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val binding = ItemPhoneBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class PhoneViewHolder(
        private val binding: ItemPhoneBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(phone: VacancyDetailModel.PhoneModel) {
            binding.apply {
                tvPhone.text = phone.formatted
                tvPhone.setOnClickListener {
                    onPhoneClick(phone.formatted)
                }
                tvPhoneComment.text = phone.comment
            }
        }
    }
}
