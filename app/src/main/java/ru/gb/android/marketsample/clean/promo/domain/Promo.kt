package ru.gb.android.marketsample.clean.promo.domain

sealed class Promo (
    open val id: String,
    val name: String,
    val image: String,
    val description: String,
    val discount: Double,
)