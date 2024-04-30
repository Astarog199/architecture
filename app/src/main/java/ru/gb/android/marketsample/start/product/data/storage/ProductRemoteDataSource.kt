package ru.gb.android.marketsample.start.product.data.storage

import ru.gb.android.marketsample.start.product.data.api.ProductApiService
import ru.gb.android.marketsample.start.product.data.api.ProductDto

data class ProductRemoteDataSource(
    private val productApiService: ProductApiService,
){
    suspend fun getProducts(): List<ProductDto>{
        return productApiService.getProducts()
    }
}
