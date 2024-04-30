package ru.gb.android.marketsample.clean.promo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.gb.android.marketsample.databinding.ItemPromoBinding
import ru.gb.android.marketsample.clean.promo.data.PromoEntity

class PromoAdapter : ListAdapter<PromoEntity, PromoHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoHolder {
        return PromoHolder(
            ItemPromoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PromoHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<PromoEntity>() {

        override fun areItemsTheSame(oldItem: PromoEntity, newItem: PromoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PromoEntity, newItem: PromoEntity): Boolean {
            return oldItem == newItem
        }
    }
}


