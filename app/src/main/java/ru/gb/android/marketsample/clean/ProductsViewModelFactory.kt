package ru.gb.android.marketsample.clean

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.marketsample.clean.product.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.product.presentation.ProductVOFactory
import ru.gb.android.marketsample.clean.product.presentation.ProductsViewModel
import ru.gb.android.marketsample.clean.promo.domain.ConsumePromosUseCase
import ru.gb.android.marketsample.clean.promo.presentation.PromoVOMapper
import ru.gb.android.marketsample.clean.promo.presentation.PromoViewModel

class ProductsViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
    private val promoVOMapper: PromoVOMapper,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    productVOFactory = productVOFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }

            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoViewModel(
                    consumePromosUseCase = consumePromosUseCase,
                    promoVOMapper = promoVOMapper,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
