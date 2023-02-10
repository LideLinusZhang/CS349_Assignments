package ca.uwaterloo.a1enhanced.view

import ca.uwaterloo.a1enhanced.controller.CodeTextField
import ca.uwaterloo.a1enhanced.controller.NameTextField
import ca.uwaterloo.a1enhanced.controller.ScoreTextField
import ca.uwaterloo.a1enhanced.controller.TermChoiceBox
import ca.uwaterloo.a1enhanced.model.Course
import ca.uwaterloo.a1enhanced.model.CourseList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*

class CourseHBox(private val course: Course) : HBox(), IView {
    private val codeTextField = CodeTextField().apply {
        isEditable = false
        alignment = Pos.CENTER
    }
    private val nameTextField = NameTextField().apply {
        textProperty().addListener { _, _, _ -> toChangeMode() }
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
            nameTextField,
            scoreTextField,
            termChoiceBox,
            updateButton,
            deleteAndUndoButton
        )
        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(it, Insets(10.0, 10.0, 10.0, 0.0))
        }
        setHgrow(nameTextField, Priority.ALWAYS)
        padding = Insets(0.0, 0.0, 0.0, 10.0)

        course.registerView(this)
        update()
    }

    private fun setBackgroundColor() {
        val backgroundColor: Color = when (course.scoreProperty.value) {
            null -> DARKSLATEGRAY
            in 0..49 -> LIGHTCORAL
            in 50..59 -> LIGHTBLUE
            in 60..90 -> LIGHTGREEN
            in 91..95 -> SILVER
            else -> GOLD
        }
        background = Background(BackgroundFill(backgroundColor, CornerRadii(10.0), Insets(2.5)))
    }

    private fun commitChanges() {
        toDisplayMode()
        course.nameProperty.value = nameTextField.text
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
        nameTextField.text = course.nameProperty.value
        termChoiceBox.value = course.termProperty.value
        scoreTextField.text =
            if (course.scoreProperty.value == null) "WD"
            else course.scoreProperty.value.toString()
        toDisplayMode()
    }
}