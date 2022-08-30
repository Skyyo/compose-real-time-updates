package com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalLifecycleComposeApi::class)
@FlowPreview
@Composable
fun LifecycleAwareCurrenciesScreen(viewModel: LifecycleAwareCurrenciesViewModel) {
    val currencyPrices = viewModel.currencyPrices.collectAsStateWithLifecycle()

    LazyColumn {
        itemsIndexed(currencyPrices.value, { _, item -> item.id }) { index, currencyPrice ->
            LifecycleAwareCurrencyPriceCard(
                currencyPrice = currencyPrice,
                currencyPriceUpdateFlow = viewModel.provideCurrencyUpdateFlow(),
                onDisposed = { viewModel.onDisposed(index) },
                onCurrencyUpdated = { newPrice -> viewModel.onCurrencyUpdated(newPrice, index) })
        }
    }
}