package ru.gb.android.marketsample.start.product.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.start.product.data.ProductEntity
import ru.gb.android.marketsample.start.product.data.storage.ProductLocalDataSource
import ru.gb.android.marketsample.start.product.data.storage.ProductRemoteDataSource

class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch {
            val products = productRemoteDataSource.getProducts()

            productLocalDataSource.saveProducts(
                products.map { productDto ->
                    ProductEntity(
                        id = productDto.id,
                        name = productDto.name,
                        image = productDto.image,
                        price = productDto.price,
                    )
                }
            )
        }

        return productLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }
}
