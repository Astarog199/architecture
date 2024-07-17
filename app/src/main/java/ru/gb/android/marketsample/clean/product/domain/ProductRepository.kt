package ru.gb.android.marketsample.clean.product.domain

import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun consumeProducts() : Flow<List<Product>>
}