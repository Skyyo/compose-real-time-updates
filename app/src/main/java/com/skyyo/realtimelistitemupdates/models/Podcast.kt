package com.skyyo.realtimelistitemupdates.models

import androidx.compose.runtime.Immutable

@Immutable
data class Podcast(
    val id: Int,
    val title: String,
    val downloadProgress: Int
)