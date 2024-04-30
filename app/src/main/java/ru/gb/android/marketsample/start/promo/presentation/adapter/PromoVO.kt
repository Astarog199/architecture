package ru.gb.android.marketsample.start.promo.presentation.adapter

data class PromoVO (
    val id: String,
    val name: String,
    val image: String,
    val discount: Double,
    val description: String,
    val type: String,
    val products: List<String>,
)