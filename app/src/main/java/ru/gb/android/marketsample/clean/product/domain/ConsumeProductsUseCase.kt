package ru.gb.android.marketsample.clean.product.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.clean.product.data.ProductEntity
import ru.gb.android.marketsample.clean.product.data.ProductRepositoryImpl

class ConsumeProductsUseCase(
    productRepositoryImpl: ProductRepositoryImpl
) {
    private val productApiService = productRepositoryImpl.getProductApiService()
    private val productLocalDataSource = productRepositoryImpl.getProductLocalDataSource()
    private val coroutineDispatcher = productRepositoryImpl.getProductDispatcher()

    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch {
            val products = productApiService.getProducts()

            productLocalDataSource.saveProducts(
                products.map { productDto ->
                    ProductEntity(
                        id = productDto.id,
                        name = productDto.name,
                        image = productDto.image,
                        price = productDto.price,
                        hasDiscount = false,
                        discount = 0,
                    )
                }
            )
        }

        return productLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }
}