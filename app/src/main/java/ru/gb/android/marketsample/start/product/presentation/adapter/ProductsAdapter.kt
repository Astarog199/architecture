package ru.gb.android.marketsample.start.product.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.gb.android.marketsample.databinding.ItemProductBinding

class ProductsAdapter : ListAdapter<ProductVO, ProductHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ProductVO>() {

        override fun areItemsTheSame(oldItem: ProductVO, newItem: ProductVO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductVO, newItem: ProductVO): Boolean {
            return oldItem == newItem
        }
    }
}


