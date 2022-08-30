package com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.skyyo.realtimelistitemupdates.common.CurrencyCard
import com.skyyo.realtimelistitemupdates.models.CurrencyPrice
import kotlinx.coroutines.flow.Flow

@Composable
fun LifecycleAwareCurrencyPriceCard(
    currencyPrice: CurrencyPrice,
    currencyPriceUpdateFlow: Flow<Int>,
    onCurrencyUpdated: (progress: Int) -> Unit,
    onDisposed: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleAwareCurrencyPriceFlow = remember(currencyPriceUpdateFlow, lifecycleOwner) {
        currencyPriceUpdateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    LaunchedEffect(Unit) {
        lifecycleAwareCurrencyPriceFlow.collect { progress -> onCurrencyUpdated(progress) }
    }

    DisposableEffect(Unit) { onDispose { onDisposed() } }

    CurrencyCard(currencyPrice.name, "${currencyPrice.price}", currencyPrice.priceFluctuation)
}