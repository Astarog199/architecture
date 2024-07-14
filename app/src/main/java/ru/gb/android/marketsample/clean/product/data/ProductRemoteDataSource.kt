package ru.gb.android.marketsample.clean.product.data

class ProductRemoteDataSource(
    private val productApiService: ProductApiService
) {
    suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
