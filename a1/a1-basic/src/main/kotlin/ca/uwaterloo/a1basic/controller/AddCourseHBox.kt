package ca.uwaterloo.a1basic.controller

import ca.uwaterloo.a1basic.model.CourseList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

object AddCourseHBox : HBox() {
    private val codeTextField = CodeTextField()
    private val nameTextField = NameTextField()
    private val scoreTextField = ScoreTextField()
    private val termChoiceBox = TermChoiceBox()
    private val addCourseButton = Button("Create").apply {
        alignment = Pos.CENTER
        prefWidth = 70.0
        prefHeight = 25.0
        onAction = EventHandler { addCourse() }
    }

    init {
        children.addAll(codeTextField, nameTextField, scoreTextField, termChoiceBox, addCourseButton)
        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(it, Insets(10.0, 10.0, 10.0, 0.0))
        }
        setHgrow(nameTextField, Priority.ALWAYS)
        maxWidth = Double.MAX_VALUE
        minWidth = USE_PREF_SIZE
        padding = Insets(0.0, 10.0, 0.0, 10.0)
        setMargin(addCourseButton, Insets(10.0, 80.0, 10.0, 0.0))
    }

    private fun addCourse() {
        if (codeTextField.text.isNotEmpty() && scoreTextField.text.isNotEmpty() && termChoiceBox.value != null) {
            CourseList.addCourse(
                codeTextField.text,
                scoreTextField.text.toIntOrNull(),
                nameTextField.text,
                termChoiceBox.value
            )
            reset()
        }
    }

    private fun reset() {
        codeTextField.text = ""
        nameTextField.text = ""
        scoreTextField.text = ""
        termChoiceBox.value = null
    }
}