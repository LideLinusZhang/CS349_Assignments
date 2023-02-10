package ca.uwaterloo.a1basic.controller

import ca.uwaterloo.a1basic.model.CourseList
import ca.uwaterloo.a1basic.model.CourseType
import ca.uwaterloo.a1basic.model.SortBy
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.text.TextAlignment

object SortAndFilterCourseHBox : HBox() {
    private val sortByLabel = Label("Sort by:").apply {
        textAlignment = TextAlignment.CENTER
        alignment = Pos.CENTER
    }
    private val sortByChoiceBox = ChoiceBox<SortBy>().apply {
        items.addAll(SortBy.values())

        value = CourseList.sortBy
        valueProperty().addListener { _, _, newValue -> CourseList.sortBy = newValue }
    }
    private val filterByLabel = Label("Filter by:").apply {
        textAlignment = TextAlignment.CENTER
        alignment = Pos.CENTER
    }
    private val filterByChoiceBox = ChoiceBox<CourseType>().apply {
        items.addAll(CourseType.values())
        value = CourseList.courseType
        valueProperty().addListener { _, _, newValue -> CourseList.courseType = newValue }
    }
    private val includeWDBox = CheckBox("Include WD").apply {
        isSelected = CourseList.includeWD
        selectedProperty().addListener { _, _, newValue -> CourseList.includeWD = newValue }
    }

    init {
        maxWidth = Double.MAX_VALUE
        minWidth = USE_PREF_SIZE
        children.addAll(
            sortByLabel,
            sortByChoiceBox,
            Separator(Orientation.VERTICAL),
            filterByLabel,
            filterByChoiceBox,
            Separator(Orientation.VERTICAL),
            includeWDBox
        )
        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(
                it, when (it) {
                    is Label -> Insets(5.0, 7.5, 7.5, 10.0)
                    is ChoiceBox<*> -> Insets(5.0, 10.0, 7.5, 0.0)
                    is CheckBox -> Insets(5.0, 10.0, 7.5, 10.0)
                    else -> Insets(0.0)
                }
            )
        }

        alignment = Pos.CENTER_LEFT
    }
}