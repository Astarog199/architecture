package ru.gb.android.marketsample.clean.promo.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.clean.promo.data.PromoEntity
import ru.gb.android.marketsample.clean.promo.data.PromoRepositoryImpl
import ru.gb.android.marketsample.layered.common.promo.data.PromoDataMapper
import ru.gb.android.marketsample.layered.common.promo.data.PromoLocalDataSource
import ru.gb.android.marketsample.layered.common.promo.data.PromoRemoteDataSource
import ru.gb.android.marketsample.layered.common.promo.data.PromoRepository
import ru.gb.android.marketsample.layered.common.promo.domain.Promo
import ru.gb.android.marketsample.layered.common.promo.domain.PromoDomainMapper

class ConsumePromosUseCase(
    private val promoRepository: PromoRepository,
    private val promoDomainMapper: PromoDomainMapper,
) {

    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .map{ promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}

