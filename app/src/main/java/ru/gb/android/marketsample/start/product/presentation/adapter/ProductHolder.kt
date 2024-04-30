package ru.gb.android.marketsample.start.product.presentation.adapter

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import ru.gb.android.marketsample.databinding.ItemProductBinding
import coil.load

class ProductHolder(
    private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductVO) {
        binding.image.load(product.image)
        binding.name.text = product.name
        binding.price.text = "${product.price} руб"
        if(product.hasDiscount) {
            binding.promo.visibility = VISIBLE
            binding.promo.text = "${product.discount} %"
        } else {
            binding.promo.visibility = GONE
        }

    }
}
