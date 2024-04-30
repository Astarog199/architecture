package ru.gb.android.marketsample.clean.promo.data

import kotlinx.coroutines.CoroutineDispatcher
import ru.gb.android.marketsample.clean.promo.domain.PromoRepository

class PromoRepositoryImpl(
    private val promoLocalDataSource: PromoLocalDataSource,
    private val promoApiService: PromoApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
) : PromoRepository {
    override fun getPromoLocalDataSource(): PromoLocalDataSource {
        return promoLocalDataSource
    }

    override fun getPromoApiService(): PromoApiService {
        return promoApiService
    }

    override fun getCoroutineDispatcher(): CoroutineDispatcher {
        return coroutineDispatcher
    }
}
