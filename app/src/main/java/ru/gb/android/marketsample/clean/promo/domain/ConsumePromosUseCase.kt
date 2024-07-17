package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.flow.Flow


class ConsumePromosUseCase(
    private val promoRepository: PromoRepository
) {

    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
    }
}

