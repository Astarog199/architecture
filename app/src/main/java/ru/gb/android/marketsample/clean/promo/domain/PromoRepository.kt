package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.flow.Flow
import ru.gb.android.marketsample.clean.promo.data.PromoEntity


interface PromoRepository {
    fun consumePromos(): Flow<List<PromoEntity>>
}