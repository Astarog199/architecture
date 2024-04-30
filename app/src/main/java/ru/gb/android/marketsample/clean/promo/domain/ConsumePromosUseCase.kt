package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.clean.promo.data.PromoEntity
import ru.gb.android.marketsample.clean.promo.data.PromoRepositoryImpl

class ConsumePromosUseCase(
    promoRepository: PromoRepositoryImpl
) {

    private val promoApiService = promoRepository.getPromoApiService()
    private val promoLocalDataSource = promoRepository.getPromoLocalDataSource()
    private val coroutineDispatcher = promoRepository.getCoroutineDispatcher()

    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumePromos(): Flow<List<PromoEntity>> {
        scope.launch {
            val promos = promoApiService.getPromos()
            promoLocalDataSource.savePromos(
                promos.map { promo ->
                    PromoEntity(
                        id = promo.id,
                        name = promo.name,
                        image = promo.image,
                        discount = promo.discount,
                        description = promo.description,
                        type = promo.type,
                        products = promo.products ?: emptyList(),
                    )
                }
            )
        }

        return promoLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }

}

