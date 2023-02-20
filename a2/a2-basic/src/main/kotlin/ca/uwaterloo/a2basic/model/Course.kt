package ca.uwaterloo.a2basic.model

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color

class Course(val code: String, score: Int?, term: Term) {
    companion object {
        private var nextUniqueId: Int = 0
        private fun getUniqueId(): Int {
            val uniqueId = nextUniqueId
            nextUniqueId++
            return uniqueId
        }
    }

    val uniqueId = getUniqueId()
    val scoreProperty = SimpleObjectProperty<Int?>(score)
    val termProperty = SimpleObjectProperty(term)

    val isFailed: Boolean get() = scoreProperty.value != null && scoreProperty.value!! < 50
    val isMathCourse: Boolean
        get() = code.startsWith("MATH", true) ||
                code.startsWith("CO", true) ||
                code.startsWith("STAT", true)

    val isCSCourse: Boolean get() = code.startsWith("CS", true)

    fun getColor(): Color =
        when (scoreProperty.value) {
            null -> Color.DARKSLATEGRAY
            in 0..49 -> Color.LIGHTCORAL
            in 50..59 -> Color.LIGHTBLUE
            in 60..90 -> Color.LIGHTGREEN
            in 91..95 -> Color.SILVER
            else -> Color.GOLD
        }
}