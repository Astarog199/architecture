package ru.gb.android.marketsample.clean.promo.data

class PromoRemoteDataSource (
    private val promoApiService: PromoApiService
){
    suspend fun getPromos(): List<PromoDto> {
        return promoApiService.getPromos()
    }
}