package ru.gb.android.marketsample.clean.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gb.android.marketsample.clean.promo.data.PromoEntity
import ru.gb.android.marketsample.clean.product.data.ProductEntity
import ru.gb.android.marketsample.clean.product.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.promo.domain.ConsumePromosUseCase

class ProductsViewModel(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val consumePromosUseCase: ConsumePromosUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<ProductEntity>>(listOf())
    val items: StateFlow<List<ProductEntity>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val isError: SharedFlow<Boolean> = _isError.asSharedFlow()

    init {
        requestProducts()
    }

    fun refresh() {
        requestProducts()
    }

    private fun requestProducts() {
        _isLoading.value = true
        consumeProductsUseCase.consumeProducts()
            .flatMapLatest { products ->
                consumePromosUseCase.consumePromos().map { promos -> products to promos }
            }
            .map { (products, promos) ->
                products.map { product ->
                    val promoForProduct: PromoEntity? = promos.firstOrNull { promo ->
                        promo.products.any { productId -> productId == product.id }
                    }

                    product.copy(
                        hasDiscount = promoForProduct != null,
                        discount = promoForProduct?.discount?.toInt() ?: 0
                    )
                }
            }
            .onEach { productVOs ->
                _isLoading.value = false
                _items.value = productVOs
            }
            .catch {
                _isLoading.value = false
                _isError.tryEmit(true)
            }
            .launchIn(viewModelScope)
    }
}
