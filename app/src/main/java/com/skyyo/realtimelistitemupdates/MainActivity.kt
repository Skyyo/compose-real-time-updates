package com.skyyo.realtimelistitemupdates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skyyo.realtimelistitemupdates.currencyPrices.CurrenciesScreen
import com.skyyo.realtimelistitemupdates.currencyPrices.CurrenciesViewModel
import com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware.LifecycleAwareCurrenciesScreen
import com.skyyo.realtimelistitemupdates.currencyPricesLifecycleAware.LifecycleAwareCurrenciesViewModel
import com.skyyo.realtimelistitemupdates.podcasts.PodcastsScreen
import com.skyyo.realtimelistitemupdates.podcasts.PodcastsViewModel
import com.skyyo.realtimelistitemupdates.ui.theme.RealTimeListItemUpdatesTheme
import kotlinx.coroutines.FlowPreview

@FlowPreview
class MainActivity : ComponentActivity() {
    private val currenciesViewModel by viewModels<CurrenciesViewModel>()
    private val lifecycleAwareCurrenciesViewModel by viewModels<LifecycleAwareCurrenciesViewModel>()
    private val podcastsViewModel by viewModels<PodcastsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RealTimeListItemUpdatesTheme {
                Surface(color = Color.White) {
//                    Row {
//                        Box(Modifier.weight(1f)) {
//                            LifecycleAwareCurrenciesScreen(lifecycleAwareCurrenciesViewModel)
//                        }
//                        Box(Modifier.weight(1f)) {
//                            PodcastsScreen(podcastsViewModel)
//                        }
//                    }
//                    CurrenciesScreen(currenciesViewModel)
                    LifecycleAwareCurrenciesScreen(lifecycleAwareCurrenciesViewModel)
//                    PodcastsScreen(podcastsViewModel)
                }
            }
        }
    }
}
