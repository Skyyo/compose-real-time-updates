package com.skyyo.realtimelistitemupdates.podcasts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun PodcastsScreen(viewModel: PodcastsViewModel) {
    val podcasts = viewModel.podcasts.collectAsState()
    LazyColumn {
        itemsIndexed(podcasts.value) { index, podcast ->
            PodcastCard(
                podcast = podcast,
                onDownloadClick = { viewModel.onDownloadPodcastClicked(podcast.id, index) })
        }
    }
}