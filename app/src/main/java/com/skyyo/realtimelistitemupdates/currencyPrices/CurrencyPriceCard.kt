package com.skyyo.realtimelistitemupdates.currencyPrices

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.skyyo.realtimelistitemupdates.common.CurrencyCard
import com.skyyo.realtimelistitemupdates.models.CurrencyPrice

@Composable
fun CurrencyPriceCard(
    currencyPrice: CurrencyPrice,
    onActive: () -> Unit,
    onDisposed: () -> Unit,
) {
    LaunchedEffect(Unit) { onActive() }
    DisposableEffect(Unit) { onDispose { onDisposed() } }
    CurrencyCard(currencyPrice.name, "${currencyPrice.price}", currencyPrice.priceFluctuation)
}