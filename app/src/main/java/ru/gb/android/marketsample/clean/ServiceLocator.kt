package ru.gb.android.marketsample.clean

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
import ru.gb.android.marketsample.clean.product.data.ProductApiService
import ru.gb.android.marketsample.clean.product.data.ProductLocalDataSource
import ru.gb.android.marketsample.clean.product.data.ProductRepositoryImpl
import ru.gb.android.marketsample.clean.product.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.promo.data.PromoApiService
import ru.gb.android.marketsample.clean.promo.data.PromoLocalDataSource
import ru.gb.android.marketsample.clean.promo.data.PromoRepositoryImpl
import ru.gb.android.marketsample.clean.promo.domain.ConsumePromosUseCase

object ServiceLocator {

    const val ENDPOINT = "https://makzimi.github.io/"

    lateinit var applicationContext: Context

    private var productRepositorySingleton: ProductRepositoryImpl? = null
    private var promoRepositorySingleton: PromoRepositoryImpl? = null
    private var retrofitSingleton: Retrofit? = null

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            consumeProductsUseCase = provideConsumeProductUseCase(),
            consumePromosUseCase = provideConsumePromosUseCase(),
        )
    }

    private fun provideConsumePromosUseCase(): ConsumePromosUseCase {
        return ConsumePromosUseCase(
            promoRepository = providePromoRepository()
        )
    }

    private fun provideConsumeProductUseCase(): ConsumeProductsUseCase {
        return ConsumeProductsUseCase(productRepositoryImpl = provideProductRepository())
    }

    private fun providePromoRepository(): PromoRepositoryImpl {
        val local = promoRepositorySingleton
        return local ?: run {
            val newPromoRepository = PromoRepositoryImpl(
                promoLocalDataSource = providePromoLocalDataSource(),
                promoApiService = providePromoApiService(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            promoRepositorySingleton = newPromoRepository
            newPromoRepository
        }
    }

    private fun providePromoLocalDataSource(): PromoLocalDataSource {
        return PromoLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private fun providePromoApiService(): PromoApiService {
        return provideRetrofit().create(PromoApiService::class.java)
    }

    private fun provideProductRepository(): ProductRepositoryImpl {
        val local = productRepositorySingleton
        return local ?: run {
            val newProductRepository = ProductRepositoryImpl(
                productLocalDataSource = provideProductLocalDataSource(),
                productApiService = provideProductApiService(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            productRepositorySingleton = newProductRepository
            newProductRepository
        }
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
