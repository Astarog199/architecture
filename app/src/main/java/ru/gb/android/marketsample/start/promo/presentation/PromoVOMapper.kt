package ru.gb.android.marketsample.start.promo.presentation

import ru.gb.android.marketsample.start.common.promo.data.PromoEntity
import ru.gb.android.marketsample.start.promo.presentation.adapter.PromoVO

class PromoVOMapper {
    fun map(promoEntity: PromoEntity) = PromoVO(
        id = promoEntity.id,
        name = promoEntity.name,
        image = promoEntity.image,
        discount = promoEntity.discount,
        description = promoEntity.description,
        type = promoEntity.type,
        products =  promoEntity.products,
    )
}