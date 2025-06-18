package com.dev.randomcityapp.util

import androidx.compose.ui.graphics.Color
import java.util.*

fun colorFromName(name: String): Color = when (name.lowercase(Locale.US)) {
    "red" -> Color.Red
    "blue" -> Color.Blue
    "green" -> Color.Green
    "yellow" -> Color.Yellow
    "black" -> Color.Black
    "white" -> Color.White
    else -> Color.Gray
}
