package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.CoroutineDispatcher
import ru.gb.android.marketsample.clean.promo.data.PromoApiService
import ru.gb.android.marketsample.clean.promo.data.PromoLocalDataSource

interface PromoRepository {

    fun getPromoLocalDataSource(): PromoLocalDataSource

    fun getPromoApiService() : PromoApiService

    fun getCoroutineDispatcher() : CoroutineDispatcher

}