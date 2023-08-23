package com.example.loadingindicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loadingindicator.ui.theme.CircularDotsIndicatorTheme
import java.lang.Math.toRadians
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

const val TAG = "Indicator"

@Composable
fun CircularDotsProgressIndicator(modifier: Modifier = Modifier) {
    var radius by remember { mutableStateOf(0.dp) }
    val circumference = 2 * PI * radius.value
    val dotSize = 5.dp
    val dotSpacing = 15.dp
    val numberOfDots = (circumference / (dotSize + dotSpacing).value).toInt()
    val animationDuration = 700

    val dotColor = Color.LightGray
    val dotColorHighlight = MaterialTheme.colorScheme.primary

    val infiniteTransition = rememberInfiniteTransition("CircularDotsProgressIndicator")
    val highlightedDot by infiniteTransition.animateValue(
        label = "highlighted dot",
        initialValue = 1,
        targetValue = numberOfDots,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            radius = (minOf(size.width, size.height) / 6).dp
            val centerX = width / 2
            val centerY = height / 2

            // guide circle
//            drawCircle(
//                color = Color.Green,
//                radius = radius.toPx(),
//                style = Stroke(width = 2.dp.toPx())
//            )

            // Drawing dots
            repeat(numberOfDots) { i ->
                val angle = 360f / numberOfDots * i
                val x = centerX + radius.toPx() * cos(toRadians(angle.toDouble())).toFloat()
                val y = centerY + radius.toPx() * sin(toRadians(angle.toDouble())).toFloat()
                drawCircle(
                    color = if (highlightedDot == i + 1) dotColorHighlight else dotColor,
                    radius = dotSize.toPx(),
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CircularDotsProgressIndicatorPreview() {
    CircularDotsIndicatorTheme {
        CircularDotsProgressIndicator(
            modifier = Modifier.size(width = 300.dp, height = 200.dp)
        )
    }
}