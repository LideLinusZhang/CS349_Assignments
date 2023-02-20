package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.controller.CodeTextField
import ca.uwaterloo.a2basic.controller.ScoreTextField
import ca.uwaterloo.a2basic.controller.TermChoiceBox
import ca.uwaterloo.a2basic.model.Course
import ca.uwaterloo.a2basic.model.CourseList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.*

class CourseHBox(private val course: Course) : HBox(), IView {
    private val codeTextField = CodeTextField().apply {
        isEditable = false
        alignment = Pos.CENTER
    }
    private val termChoiceBox = TermChoiceBox().apply {
        selectionModel.selectedItemProperty().addListener { _, _, _ -> toChangeMode() }
    }
    private val scoreTextField = ScoreTextField().apply {
        textProperty().addListener { _, _, _ -> toChangeMode() }
    }
    private val updateButton: Button = Button("Update").apply {
        alignment = Pos.CENTER
        isDisable = true
        prefWidth = 70.0
        prefHeight = 25.0
        onAction = EventHandler { commitChanges() }
    }
    private val deleteAndUndoButton: Button = Button("Delete").apply {
        alignment = Pos.CENTER
        prefWidth = 70.0
        prefHeight = 25.0
        onAction = EventHandler { delete() }
    }

    init {
        maxWidth = Double.MAX_VALUE
        minWidth = USE_PREF_SIZE
        children.addAll(
            codeTextField,
            scoreTextField,
            termChoiceBox,
            updateButton,
            deleteAndUndoButton
        )
        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(it, Insets(10.0, 10.0, 10.0, 0.0))
        }
        padding = Insets(0.0, 0.0, 0.0, 10.0)

        update()
    }

    private fun setBackgroundColor() {
        background = Background(BackgroundFill(course.getColor(), CornerRadii(10.0), Insets(2.5)))
    }

    private fun commitChanges() {
        toDisplayMode()
        course.scoreProperty.value = scoreTextField.text.toIntOrNull()
        course.termProperty.value = termChoiceBox.value
    }

    private fun revertChanges() {
        toDisplayMode()
        update()
    }

    private fun toDisplayMode() {
        updateButton.isDisable = true
        deleteAndUndoButton.text = "Delete"
        deleteAndUndoButton.onAction = EventHandler { delete() }
    }

    private fun toChangeMode() {
        updateButton.isDisable = false
        deleteAndUndoButton.text = "Undo"
        deleteAndUndoButton.onAction = EventHandler { revertChanges() }
    }

    private fun delete() {
        CourseList.deleteCourse(course.uniqueId)
    }

    override fun update() {
        setBackgroundColor()
        codeTextField.text = course.code
        termChoiceBox.value = course.termProperty.value
        scoreTextField.text =
            if (course.scoreProperty.value == null) "WD"
            else course.scoreProperty.value.toString()
        toDisplayMode()
    }
}