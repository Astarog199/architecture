package ru.gb.android.marketsample.clean.product.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch



class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productDataMapper: ProductDataMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch {
            val products = productRemoteDataSource.getProducts()
            productLocalDataSource.saveProducts(
                products.map(productDataMapper::toEntity)
            )
        }

        return productLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }
}
