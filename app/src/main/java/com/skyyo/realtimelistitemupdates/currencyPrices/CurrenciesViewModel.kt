package com.skyyo.realtimelistitemupdates.currencyPrices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyyo.realtimelistitemupdates.models.CurrencyPrice
import com.skyyo.realtimelistitemupdates.models.PriceFluctuation
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.random.Random


@FlowPreview
class CurrenciesViewModel : ViewModel() {
    private val _currencyPrices = MutableStateFlow(listOf<CurrencyPrice>())
    val currencyPrices: StateFlow<List<CurrencyPrice>> get() = _currencyPrices

    private val producers: MutableMap<Int, Job> = mutableMapOf()

    init {
        getCurrencyPrices()
    }

    private fun getCurrencyPrices() {
        viewModelScope.launch(Dispatchers.Default) {
            val initialCurrencyPrices = arrayListOf<CurrencyPrice>()
            run loop@{
                Currency.getAvailableCurrencies().forEachIndexed { index, currency ->
                    if (index == 100) return@loop
                    initialCurrencyPrices += CurrencyPrice(
                        id = index,
                        name = "1 USD to ${currency.currencyCode}",
                        price = Random.nextInt(0, 100),
                        priceFluctuation = PriceFluctuation.UNKNOWN
                    )
                }
            }
            _currencyPrices.emit(initialCurrencyPrices)
        }
    }

    fun onCardActive(currencyId: Int, index: Int) {
        if (producers.containsKey(currencyId)) return
        val currencyPriceUpdateFlow: Flow<Int> = provideCurrencyUpdateFlow()
        val currencyPriceUpdateJob = viewModelScope.launch {
            observePriceUpdateFlow(index, currencyPriceUpdateFlow)
        }
        producers[currencyId] = currencyPriceUpdateJob
    }

    fun onCardDisposed(currencyId: Int) {
        if (producers.containsKey(currencyId)) {
            producers.getValue(currencyId).job.cancel()
            producers.remove(currencyId)
        }
    }

    private suspend fun observePriceUpdateFlow(index: Int, currencyPriceUpdateFlow: Flow<Int>) {
        currencyPriceUpdateFlow.collect { newPrice ->
            val newFluctuation = when {
                newPrice > _currencyPrices.value[index].price -> PriceFluctuation.UP
                else -> PriceFluctuation.DOWN
            }
            val updatedCurrencyPrice = _currencyPrices.value[index].copy(
                price = newPrice,
                priceFluctuation = newFluctuation
            )
            val mutableCurrencyPrices = _currencyPrices.value.toMutableList()
            mutableCurrencyPrices[index] = updatedCurrencyPrice
            _currencyPrices.value = mutableCurrencyPrices.toList()
        }
    }

    private fun provideCurrencyUpdateFlow(): Flow<Int> {
        return flow {
            repeat(10000) {
                delay(Random.nextLong(500L, 2500L))
                emit(Random.nextInt(0, 100))
            }
        }.flowOn(Dispatchers.Default).distinctUntilChanged()
    }
}
