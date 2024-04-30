package ru.gb.android.marketsample.clean.promo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.gb.android.marketsample.clean.promo.data.PromoRepositoryImpl
import ru.gb.android.marketsample.clean.promo.data.PromoEntity
import ru.gb.android.marketsample.clean.promo.domain.ConsumePromosUseCase

class PromoViewModel(
    private val consumePromosUseCase: ConsumePromosUseCase
) : ViewModel() {


    private val _items = MutableStateFlow<List<PromoEntity>>(listOf())
    val items: StateFlow<List<PromoEntity>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val isError: SharedFlow<Boolean> = _isError.asSharedFlow()

    init {
        requestPromos()
    }

    fun refresh() {
        requestPromos()
    }

    private fun requestPromos() {
        _isLoading.value = true
        consumePromosUseCase.consumePromos()
            .onEach { promos ->
                _isLoading.value = false
                _items.value = promos
            }
            .catch {
                _isLoading.value = false
                _isError.tryEmit(true)
            }
            .launchIn(viewModelScope)
    }
}
