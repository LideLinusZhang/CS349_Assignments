package ca.uwaterloo.a1basic.model

import ca.uwaterloo.a1basic.view.IView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

class Course(val code: String, score: Int?, term: Term, name: String) {
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
    val nameProperty = SimpleStringProperty(name)

    val isFailed: Boolean get() = scoreProperty.value != null && scoreProperty.value!! < 50
    val isMathCourse: Boolean
        get() = code.startsWith("MATH", true) ||
                code.startsWith("CO", true) ||
                code.startsWith("STAT", true)

    val isCSCourse: Boolean get() = code.startsWith("CS", true)

    fun registerView(view: IView) {
        nameProperty.addListener { _, oldValue, newValue -> if (oldValue != newValue) view.update() }
        scoreProperty.addListener { _, oldValue, newValue -> if (oldValue != newValue) view.update() }
        termProperty.addListener { _, oldValue, newValue -> if (oldValue != newValue) view.update() }
    }
}