package ca.uwaterloo.a2enhanced.view.extensions

import ca.uwaterloo.a2enhanced.model.enums.CourseType
import javafx.scene.paint.Color

fun CourseType.toColor(lightColor: Boolean = false): Color {
    return if (lightColor) {
        when (this) {
            CourseType.CS -> Color.PALEGOLDENROD
            CourseType.Math -> Color.PINK
            CourseType.Other -> Color.LIGHTBLUE
            CourseType.All -> Color.LIGHTGREEN
        }
    } else {
        when (this) {
            CourseType.CS -> Color.GOLDENROD
            CourseType.Math -> Color.PURPLE
            CourseType.Other -> Color.BLUE
            CourseType.All -> Color.GREEN
        }
    }
}