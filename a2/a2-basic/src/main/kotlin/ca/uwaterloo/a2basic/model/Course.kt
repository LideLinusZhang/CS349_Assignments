package ca.uwaterloo.a2basic.model

import javafx.beans.property.SimpleObjectProperty

class Course(val code: String, score: Int?, term: Term) {
    companion object {
        const val scoreMax: Double = 100.0
        const val scoreMin: Double = 0.0

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
}