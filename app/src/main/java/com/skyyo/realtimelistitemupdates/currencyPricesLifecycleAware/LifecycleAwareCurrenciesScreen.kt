package com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun LifecycleAwareCurrenciesScreen(viewModel: LifecycleAwareCurrenciesViewModel) {
    val currencyPrices = viewModel.currencyPrices.collectAsState()
    LazyColumn {
        itemsIndexed(currencyPrices.value) { index, currencyPrice ->
            LifecycleAwareCurrencyPriceCard(
                currencyPrice = currencyPrice,
                currencyPriceUpdateFlow = viewModel.provideCurrencyUpdateFlow(),
                onDisposed = { viewModel.onDisposed(index) },
                onCurrencyUpdated = { newPrice -> viewModel.onCurrencyUpdated(newPrice, index) })
        }
    }
}