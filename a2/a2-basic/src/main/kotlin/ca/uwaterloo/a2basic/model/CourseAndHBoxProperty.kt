package ca.uwaterloo.a2basic.model

import ca.uwaterloo.a2basic.model.enums.Term
import ca.uwaterloo.a2basic.view.CourseHBox
import ca.uwaterloo.a2basic.view.IView
import javafx.beans.binding.ObjectBinding

class CourseAndHBoxProperty(code: String, score: Int?, term: Term) : ObjectBinding<Course>() {
    private val value: Course
    val courseHBox: CourseHBox

    init {
        value = Course(code, score, term)
        courseHBox = CourseHBox(value)

        registerView(courseHBox)
        bind(value.termProperty, value.scoreProperty)
    }

    override fun computeValue(): Course {
        return value
    }

    private fun registerView(view: IView) {
        addListener { _, _, _ -> view.update() }
    }
}