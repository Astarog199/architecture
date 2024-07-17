package ru.gb.android.marketsample.clean.product.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.clean.product.domain.Product
import ru.gb.android.marketsample.clean.product.domain.ProductRepository


class ProductRepositoryImpl(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productDataMapper: ProductDataMapper,
    private val productDomainMapper: ProductDomainMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ProductRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    override fun consumeProducts(): Flow<List<Product>> {
       return saveProducts()
            .map { products -> products.map (productDomainMapper::fromEntity) }
    }

    private fun saveProducts(): Flow<List<ProductEntity>> {
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
