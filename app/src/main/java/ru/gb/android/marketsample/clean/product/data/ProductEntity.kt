package ru.gb.android.marketsample.clean.product.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductEntity (
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
    val hasDiscount: Boolean,
    val discount: Int,
)