package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.model.CourseList
import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

object CourseListScrollPane : ScrollPane(), IView {
    private val coursesVBox = VBox().apply {
        maxHeight = Double.MAX_VALUE
        minHeight = 1.0
        minWidth = USE_PREF_SIZE
    }

    init {
        maxHeight = Double.MAX_VALUE
        minWidth = USE_PREF_SIZE
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
            VBox.setMargin(it, Insets(0.0,5.0,0.0,5.0))
        }
    }
}