package com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.skyyo.realtimelistitemupdates.common.CurrencyCard
import com.skyyo.realtimelistitemupdates.models.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        launch {
            lifecycleAwareCurrencyPriceFlow.collect { progress -> onCurrencyUpdated(progress) }
        }
    }
    DisposableEffect(Unit) { onDispose { onDisposed() } }
    CurrencyCard(currencyPrice.name, "${currencyPrice.price}", currencyPrice.priceFluctuation)
}