package ru.gb.android.marketsample.clean.product.data

import kotlinx.coroutines.CoroutineDispatcher

class ProductRepositoryImpl(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productApiService: ProductApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    fun getProductLocalDataSource(): ProductLocalDataSource {
        return productLocalDataSource
    }

    fun getProductApiService(): ProductApiService {
        return productApiService
    }

    fun getProductDispatcher(): CoroutineDispatcher {
        return coroutineDispatcher
    }

}
