package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.model.CourseList
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

object CourseListScrollPane : ScrollPane(), IView {
    private val coursesVBox = VBox().apply {
        maxHeight = Double.MAX_VALUE
        maxWidth = Double.MAX_VALUE
    }

    init {
        maxHeight = Double.MAX_VALUE
        maxWidth = Double.MAX_VALUE
        isFitToHeight = false
        isFitToWidth = true
        hbarPolicy = ScrollBarPolicy.NEVER
        vbarPolicy = ScrollBarPolicy.AS_NEEDED
        content = coursesVBox

        CourseList.registerView(this)
    }

    override fun update() {
        coursesVBox.children.clear()
        CourseList.courseHBoxes.forEach {
            coursesVBox.children.add(it)
            VBox.setVgrow(it, Priority.NEVER)
        }
    }
}