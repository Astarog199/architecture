package ru.gb.android.marketsample.start.product.presentation

import ru.gb.android.marketsample.start.common.promo.data.PromoEntity
import ru.gb.android.marketsample.start.product.data.ProductEntity
import ru.gb.android.marketsample.start.product.presentation.adapter.ProductVO

class ProductVOFactory {

    fun create(
        promos: List<PromoEntity>,
        product: ProductEntity
    ): ProductVO {
        val promoForProduct: PromoEntity? = promos.firstOrNull { promo ->
            promo.products.any { productId -> productId == product.id }
        }

        return ProductVO(
            id = product.id,
            name =  product.name,
            image =  product.image,
            price =  product.price,
            hasDiscount =  promoForProduct != null,
            discount =  promoForProduct?.discount?.toInt() ?: 0
        )
    }
}