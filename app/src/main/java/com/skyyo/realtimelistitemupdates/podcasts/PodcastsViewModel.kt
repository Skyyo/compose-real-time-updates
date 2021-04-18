package com.skyyo.realtimelistitemupdates.podcasts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyyo.realtimelistitemupdates.models.Podcast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random


@FlowPreview
class PodcastsViewModel : ViewModel() {
    private val _podcasts = MutableStateFlow(listOf<Podcast>())
    val podcasts: StateFlow<List<Podcast>> get() = _podcasts

    private val downloadQueue: MutableMap<Int, Flow<Int>> = mutableMapOf()

    init {
        getPodcasts()
    }

    private fun getPodcasts() {
        viewModelScope.launch(Dispatchers.Default) {
            val initialPodcasts = arrayListOf<Podcast>()
            repeat(100) {
                initialPodcasts += Podcast(
                    id = it,
                    title = "Podcast #$it",
                    downloadProgress = 0
                )
            }
            _podcasts.emit(initialPodcasts)
        }
    }

    fun onDownloadPodcastClicked(podcastId: Int, index: Int) {
        if (downloadQueue.containsKey(podcastId)) return
        val download: Flow<Int> = provideDownloadFlow(podcastId)
        downloadQueue[podcastId] = download
        observeDownload(index, download)
    }

    private fun provideDownloadFlow(podcastId: Int): Flow<Int> {
        return flow {
            var progress = 10
            emit(progress)
            repeat(100) {
                progress += Random.nextInt(10, 25)
                delay(500L)
                if (progress >= 100) emit(100) else emit(progress)
                if (progress >= 100) {
                    downloadQueue.remove(podcastId)
                    return@flow
                }
            }
        }.flowOn(Dispatchers.Default)
    }

    private fun observeDownload(index: Int, downloadFlow: Flow<Int>) {
        viewModelScope.launch(Dispatchers.Default) {
            downloadFlow.collect { progress ->
                val updatedPodcast = _podcasts.value[index].copy(downloadProgress = progress)
                val mutablePodcasts = _podcasts.value.toMutableList()
                mutablePodcasts[index] = updatedPodcast
                _podcasts.value = mutablePodcasts.toList()
            }
        }
    }

}
