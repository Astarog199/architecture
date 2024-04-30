package ru.gb.android.marketsample.start.product.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductEntity (
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
)