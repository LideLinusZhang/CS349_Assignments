package ca.uwaterloo.a2basic

import ca.uwaterloo.a2basic.controller.AddCourseHBox
import ca.uwaterloo.a2basic.view.CourseListScrollPane
import ca.uwaterloo.a2basic.view.CourseStatsHBox
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.PopupControl.USE_PREF_SIZE
import javafx.scene.control.Separator
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MyMarkVisualizationApplication : Application() {
    override fun start(stage: Stage) {
        val vBox = VBox(
            Separator(Orientation.HORIZONTAL),
            AddCourseHBox,
            Separator(Orientation.HORIZONTAL),
            CourseListScrollPane,
            Separator(Orientation.HORIZONTAL),
            CourseStatsHBox
        ).apply {
            maxWidth = Double.MAX_VALUE
            minWidth = USE_PREF_SIZE
            maxHeight = Double.MAX_VALUE
            children.forEach {
                VBox.setVgrow(it, Priority.NEVER)
            }
            VBox.setVgrow(CourseListScrollPane, Priority.ALWAYS)
        }

        val scene = Scene(vBox, 800.0, 600.0)

        stage.title = "CS349 - A2 My Mark Visualization - l625zhan"
        stage.scene = scene
        stage.sizeToScene()
        stage.show()
    }
}