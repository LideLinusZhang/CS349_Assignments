package ca.uwaterloo.a2enhanced.view.tabs

import ca.uwaterloo.a2enhanced.model.Course
import ca.uwaterloo.a2enhanced.model.CourseList
import ca.uwaterloo.a2enhanced.model.enums.CourseType
import ca.uwaterloo.a2enhanced.plots.PieChart
import ca.uwaterloo.a2enhanced.view.IView
import ca.uwaterloo.a2enhanced.view.extensions.toColor
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

object OutcomePieChart : VBox(), IView {
    private val pieChart = PieChart()
    private val includingMissingCheckBox = CheckBox("Include Missing Courses").apply {
        isSelected = false
        selectedProperty().addListener { _, _, _ -> update() }
    }

    init {
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
        minWidth = 1.0
        minHeight = 1.0
        alignment = Pos.CENTER
        children.addAll(pieChart, includingMissingCheckBox)
        setVgrow(includingMissingCheckBox, Priority.NEVER)
        setVgrow(pieChart, Priority.ALWAYS)
        setMargin(includingMissingCheckBox, Insets(5.0, 0.0, 5.0, 0.0))
        CourseList.registerView(this)
    }

    override fun update() {
        pieChart.clear()

        CourseList.courses.forEach {
            pieChart.addElement(it.scoreProperty.value.toColor(), it.code)
        }

        if (includingMissingCheckBox.isSelected) {
            val coursesMissing =
                (CourseType.All.creditRequired() / Course.creditPerCourse).toInt() - CourseList.courses.size
            println("Course Missing = $coursesMissing")
            for (i in 0 until coursesMissing) {
                pieChart.addElement(Color.DIMGRAY, "")
            }
        }

        pieChart.draw()
    }
}