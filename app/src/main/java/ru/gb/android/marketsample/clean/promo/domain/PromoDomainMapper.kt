package ru.gb.android.marketsample.clean.promo.domain

import ru.gb.android.marketsample.clean.promo.data.PromoEntity

class PromoDomainMapper {
    fun fromEntity(promoEntity: PromoEntity): Promo {
        return if (promoEntity.type == "product") {
            Promo.PromoForProducts(
                id = promoEntity.id,
                name = promoEntity.name,
                image = promoEntity.image,
                description = promoEntity.description,
                discount = promoEntity.discount,
                products = promoEntity.products
            )
        } else {
            Promo.PromoForPrice(
                id = promoEntity.id,
                name = promoEntity.name,
                image = promoEntity.image,
                description = promoEntity.description,
                discount = promoEntity.discount
            )
        }
    }
}