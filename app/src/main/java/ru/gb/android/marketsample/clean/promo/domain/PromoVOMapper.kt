package ru.gb.android.marketsample.clean.promo.domain

import ru.gb.android.marketsample.clean.promo.presentation.PromoVO


class PromoVOMapper {
    fun map(promo: Promo) : PromoVO {
        return PromoVO (
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description
        )
    }
}