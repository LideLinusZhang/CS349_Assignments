package ca.uwaterloo.a2enhanced.model

import ca.uwaterloo.a2enhanced.model.enums.CourseType
import ca.uwaterloo.a2enhanced.model.enums.Term
import javafx.beans.property.SimpleObjectProperty

class Course(val code: String, score: Int?, term: Term) {
    companion object {
        const val scoreMax: Double = 100.0
        const val scoreMin: Double = 0.0
        const val creditPerCourse: Double = 0.5

        private var nextUniqueId: Int = 0
        private fun getUniqueId(): Int {
            val uniqueId = nextUniqueId
            nextUniqueId++
            return uniqueId
        }
    }

    private val isMathCourse: Boolean
        get() = code.startsWith("MATH", true) ||
                code.startsWith("CO", true) ||
                code.startsWith("STAT", true)
    private val isCSCourse: Boolean get() = code.startsWith("CS", true)

    val uniqueId = getUniqueId()
    val scoreProperty = SimpleObjectProperty<Int?>(score)
    val termProperty = SimpleObjectProperty(term)

    val isWD: Boolean get() = scoreProperty.value == null
    val isFailed: Boolean get() = !isWD && scoreProperty.value!! < 50
    val type: CourseType
        get() {
            return if (isCSCourse) CourseType.CS
            else if (isMathCourse) CourseType.Math
            else CourseType.Other
        }
}