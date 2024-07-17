package ru.gb.android.marketsample.clean.product.domain

import kotlinx.coroutines.flow.Flow

class ConsumeProductsUseCase(
    private val productRepository: ProductRepository,
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.consumeProducts()
    }
}