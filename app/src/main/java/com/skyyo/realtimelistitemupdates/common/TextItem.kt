package com.skyyo.realtimelistitemupdates.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextItem(
    title: String,
    modifier: Modifier = Modifier.padding(16.dp),
    textAlign: TextAlign = TextAlign.Center,
    color : Color = Color.Black
) {
    Text(
        text = title,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
    )
}