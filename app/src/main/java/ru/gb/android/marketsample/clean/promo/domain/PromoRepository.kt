package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.flow.Flow


interface PromoRepository {
    fun consumePromos(): Flow<List<Promo>>
}