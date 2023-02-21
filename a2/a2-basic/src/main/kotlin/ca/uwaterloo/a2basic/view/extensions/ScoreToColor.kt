package ca.uwaterloo.a2basic.view.extensions

import javafx.scene.paint.Color

fun Double.toColor(): Color {
    return when (coerceIn(0.0, 100.0)) {
        in 0.0..49.0 -> Color.LIGHTCORAL
        in 50.0..59.0 -> Color.LIGHTBLUE
        in 60.0..90.0 -> Color.LIGHTGREEN
        in 91.0..95.0 -> Color.SILVER
        else -> Color.GOLD
    }
}

fun Int?.toColor(): Color {
    return when (this) {
        null -> Color.DARKSLATEGRAY
        else -> toDouble().toColor()
    }
}