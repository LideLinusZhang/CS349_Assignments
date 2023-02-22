package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.model.enums.CourseType
import ca.uwaterloo.a2basic.plots.PieChart
import ca.uwaterloo.a2basic.view.IView
import ca.uwaterloo.a2basic.view.extensions.toColor
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
        CourseList.registerView(this)
    }

    override fun update() {
        pieChart.clear()

        CourseList.courses.forEach {
            pieChart.addElement(it.scoreProperty.value.toColor(), it.code)
        }

        if (includingMissingCheckBox.isSelected) {
            val coursesMissing = CourseType.All.creditRequired() * 2 - CourseList.courses.size
            for (i in 0 until coursesMissing) {
                pieChart.addElement(Color.DIMGRAY, "")
            }
        }

        pieChart.draw()
    }
}