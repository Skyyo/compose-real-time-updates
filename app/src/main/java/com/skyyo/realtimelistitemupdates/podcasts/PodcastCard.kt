package com.skyyo.realtimelistitemupdates.podcasts

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.skyyo.realtimelistitemupdates.R
import com.skyyo.realtimelistitemupdates.common.TextItem
import com.skyyo.realtimelistitemupdates.models.Podcast

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PodcastCard(
    podcast: Podcast,
    onDownloadClick: () -> Unit,
) {
    val title = remember { podcast.title }
    val isDownloaded = podcast.downloadProgress == 100
    val animatedProgress: Float by animateFloatAsState(podcast.downloadProgress / 100f)
    val black = Color.Black
    val white = Color.White
    val green = Color(
        ContextCompat.getColor(
            LocalContext.current,
            R.color.opacity_green
        )
    )
    Card(
        backgroundColor = white,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            TextItem(title)
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterEnd),
                    progress = animatedProgress,
                    color = if (isDownloaded) green else black
                )
                if (isDownloaded) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(8.dp)
                            .align(Alignment.CenterEnd),

                        painter = painterResource(id = R.drawable.ic_play_arrow),
                        tint = green,
                        contentDescription = "Play icon",
                    )
                } else {
                    IconButton(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterEnd),
                        onClick = onDownloadClick,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                                painter = painterResource(id = R.drawable.ic_download),
                                tint = black,
                                contentDescription = "Download icon",
                            )
                        })
                }
            }
        }
    }
}