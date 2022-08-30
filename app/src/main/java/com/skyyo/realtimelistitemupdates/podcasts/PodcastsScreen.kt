package com.skyyo.realtimelistitemupdates.podcasts

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
fun PodcastsScreen(viewModel: PodcastsViewModel) {
    val podcasts by viewModel.podcasts.collectAsStateWithLifecycle()
    LazyColumn {
        itemsIndexed(podcasts, { _, item -> item.id }) { index, podcast ->
            PodcastCard(
                podcast = podcast,
                onDownloadClick = { viewModel.onDownloadPodcastClicked(podcast.id, index) })
        }
    }
}