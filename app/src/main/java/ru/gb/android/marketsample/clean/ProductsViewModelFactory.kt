package ru.gb.android.marketsample.clean

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.marketsample.clean.product.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.product.presentation.ProductsViewModel
import ru.gb.android.marketsample.clean.promo.domain.ConsumePromosUseCase
import ru.gb.android.marketsample.clean.promo.presentation.PromoViewModel

class ProductsViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val consumePromosUseCase: ConsumePromosUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }

            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoViewModel(
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
