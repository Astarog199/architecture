package ru.gb.android.marketsample.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.marketsample.start.product.data.repository.ProductRepository
import ru.gb.android.marketsample.start.common.promo.data.PromoRepository
import ru.gb.android.marketsample.start.product.presentation.ProductVOFactory
import ru.gb.android.marketsample.start.product.presentation.ProductsViewModel
import ru.gb.android.marketsample.start.promo.presentation.PromoVOMapper
import ru.gb.android.marketsample.start.promo.presentation.PromoViewModel

class ProductsViewModelFactory(
    private val productRepository: ProductRepository,
    private val promoRepository: PromoRepository,
    private val promoVOMapper: PromoVOMapper,
    private val productVOFactory: ProductVOFactory
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    productRepository = productRepository,
                    promoRepository = promoRepository,
                    productVOFactory = productVOFactory
                ) as T
            }

            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoViewModel(
                    promoRepository = promoRepository,
                    promoVOMapper = promoVOMapper
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
