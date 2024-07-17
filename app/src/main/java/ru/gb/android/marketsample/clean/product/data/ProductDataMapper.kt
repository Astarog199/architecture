package ru.gb.android.marketsample.clean.product.data

import ru.gb.android.marketsample.clean.product.domain.Product

class ProductDataMapper {
    fun toEntity(productDto: ProductDto): ProductEntity {
        return ProductEntity(
            id = productDto.id,
            name = productDto.name,
            image = productDto.image,
            price = productDto.price
        )
    }
}