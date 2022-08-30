package com.skyyo.realtimelistitemupdates.currencyPrices

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalLifecycleComposeApi::class)
@FlowPreview
@Composable
fun CurrenciesScreen(viewModel: CurrenciesViewModel) {
    val currencyPrices by viewModel.currencyPrices.collectAsStateWithLifecycle()

    LazyColumn {
        itemsIndexed(currencyPrices, { _, item -> item.id }) { index, currencyPrice ->
            CurrencyPriceCard(
                currencyPrice = currencyPrice,
                onActive = { viewModel.onCardActive(currencyPrice.id, index) },
                onDisposed = { viewModel.onCardDisposed(currencyPrice.id) })
        }
    }
}