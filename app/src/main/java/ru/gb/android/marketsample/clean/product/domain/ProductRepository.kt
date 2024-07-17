package ru.gb.android.marketsample.clean.product.domain

import kotlinx.coroutines.flow.Flow
import ru.gb.android.marketsample.clean.product.data.ProductEntity

interface ProductRepository {
    fun consumeProducts() : Flow<List<ProductEntity>>
}