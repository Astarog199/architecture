package ru.gb.android.marketsample.start

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.android.marketsample.start.product.data.api.ProductApiService
import ru.gb.android.marketsample.start.product.data.storage.ProductLocalDataSource
import ru.gb.android.marketsample.start.product.data.repository.ProductRepository
import ru.gb.android.marketsample.start.common.promo.data.api.PromoApiService
import ru.gb.android.marketsample.start.common.promo.data.PromoLocalDataSource
import ru.gb.android.marketsample.start.common.promo.data.PromoRepository
import ru.gb.android.marketsample.start.product.data.storage.ProductRemoteDataSource
import ru.gb.android.marketsample.start.common.promo.data.PromoRemoteDataSource
import ru.gb.android.marketsample.start.product.presentation.ProductVOFactory
import ru.gb.android.marketsample.start.promo.presentation.PromoVOMapper

object ServiceLocator {

    const val ENDPOINT = "https://makzimi.github.io/"

    lateinit var applicationContext: Context

    private var productRepositorySingleton: ProductRepository? = null
    private var promoRepositorySingleton: PromoRepository? = null
    private var retrofitSingleton: Retrofit? = null

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            productRepository = provideProductRepository(),
            promoRepository = providePromoRepository(),
            promoVOMapper = providePromoMapper(),
            productVOFactory = provideProductVOFactory()
        )
    }

    private fun provideProductVOFactory(): ProductVOFactory {
        return  ProductVOFactory()
    }

    private fun providePromoMapper(): PromoVOMapper {
        return PromoVOMapper()
    }

    private fun providePromoRepository(): PromoRepository {
        val local = promoRepositorySingleton
        return local ?: run {
            val newPromoRepository = PromoRepository(
                promoLocalDataSource = providePromoLocalDataSource(),
                promoRemoteDataSource = providePromoRemoteDataSource(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            promoRepositorySingleton = newPromoRepository
            newPromoRepository
        }
    }

    private fun providePromoRemoteDataSource(): PromoRemoteDataSource {
        return PromoRemoteDataSource(
            providePromoApiService()
        )
    }

    private fun providePromoLocalDataSource(): PromoLocalDataSource {
        return PromoLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private fun providePromoApiService(): PromoApiService {
        return provideRetrofit().create(PromoApiService::class.java)
    }

    private fun provideProductRepository(): ProductRepository {
        val local = productRepositorySingleton
        return local ?: run {
            val newProductRepository = ProductRepository(
                productLocalDataSource = provideProductLocalDataSource(),
                productRemoteDataSource = provideProductDataSource(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            productRepositorySingleton = newProductRepository
            newProductRepository
        }
    }

    private fun provideProductDataSource(): ProductRemoteDataSource {
        return ProductRemoteDataSource(
            provideProductApiService()
        )
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton
        return local ?: run {
            val newRetrofit = Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideProductApiService(): ProductApiService {
        return provideRetrofit().create(ProductApiService::class.java)
    }

    private fun provideProductLocalDataSource(): ProductLocalDataSource {
        return ProductLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "start_app")
}
