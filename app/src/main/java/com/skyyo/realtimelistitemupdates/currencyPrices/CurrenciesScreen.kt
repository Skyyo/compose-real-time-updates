package com.skyyo.realtimelistitemupdates.currencyPrices

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun CurrenciesScreen(viewModel: CurrenciesViewModel) {
    val currencyPrices = viewModel.currencyPrices.collectAsState()
    LazyColumn {
        itemsIndexed(currencyPrices.value) { index, currencyPrice ->
            CurrencyPriceCard(
                currencyPrice = currencyPrice,
                onActive = { viewModel.onCardActive(currencyPrice.id, index) },
                onDisposed = { viewModel.onCardDisposed(currencyPrice.id) })
        }
    }
}