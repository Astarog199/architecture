package ru.gb.android.marketsample.start.common.promo.data

import ru.gb.android.marketsample.start.common.promo.data.api.PromoApiService
import ru.gb.android.marketsample.start.common.promo.data.api.PromoDto

data class PromoRemoteDataSource(
    private val promoApiService: PromoApiService
){
    suspend fun getPromos(): List<PromoDto>{
        return promoApiService.getPromos()
    }
}
