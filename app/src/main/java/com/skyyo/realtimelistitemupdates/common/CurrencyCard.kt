package com.skyyo.realtimelistitemupdates.common

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.skyyo.realtimelistitemupdates.R
import com.skyyo.realtimelistitemupdates.models.PriceFluctuation

@Composable
fun CurrencyCard(title: String, price: String, fluctuation: PriceFluctuation) {
    val context = LocalContext.current
    val black = Color.Black
    val white = Color.White
    val green = remember {
        Color(ContextCompat.getColor(context, R.color.opacity_green))
    }
    val red = remember {
        Color(ContextCompat.getColor(context, R.color.opacity_red))
    }


    //TODO move to top
    val fluctuationColour = if (fluctuation == PriceFluctuation.UP) green else red

    val animatedElevation = remember { androidx.compose.animation.core.Animatable(4f) }
    val animatedTextColour = remember { Animatable(black) }
    val animatedBorderColour = remember { Animatable(white) }

    if (fluctuation != PriceFluctuation.UNKNOWN) {
        LaunchedEffect(fluctuation) {
            animatedElevation.animateTo(
                0f,
                animationSpec = tween(durationMillis = 500)
            )
            animatedElevation.animateTo(
                4f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        LaunchedEffect(fluctuation) {
            animatedTextColour.animateTo(
                fluctuationColour,
                animationSpec = tween(durationMillis = 500)
            )
        }
        LaunchedEffect(fluctuation) {
            animatedBorderColour.animateTo(
                fluctuationColour,
                animationSpec = tween(durationMillis = 500)
            )
            animatedBorderColour.animateTo(
                Color.White,
                animationSpec = tween(durationMillis = 500)
            )
        }
    }
    Card(
        backgroundColor = Color.White,
        shape = remember(animatedBorderColour) {
            RoundedCornerShape(percent = 50)
        },
        border = BorderStroke(1.dp, animatedBorderColour.value),
        elevation = animatedElevation.value.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp)
    ) {
        Row {
            TextItem(title)
            TextItem(
                price,
                color = animatedTextColour.value,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                textAlign = TextAlign.End
            )
        }
    }

}