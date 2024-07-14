package ru.gb.android.marketsample.clean.promo.presentation

import ru.gb.android.marketsample.clean.promo.domain.Promo


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