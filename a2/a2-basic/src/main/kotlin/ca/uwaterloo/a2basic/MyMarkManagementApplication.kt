package ca.uwaterloo.a2basic

import ca.uwaterloo.a2basic.controller.AddCourseHBox
import ca.uwaterloo.a2basic.view.CourseListScrollPane
import ca.uwaterloo.a2basic.view.CourseStatsHBox
import ca.uwaterloo.a2basic.view.CourseStatsTablePane
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region.USE_PREF_SIZE
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MyMarkVisualizationApplication : Application() {
    override fun start(stage: Stage) {
        val coursesVBox = VBox(
            AddCourseHBox,
            CourseListScrollPane
        ).apply {
            maxHeight = Double.MAX_VALUE
            minWidth = USE_PREF_SIZE
            children.forEach { VBox.setVgrow(it, Priority.NEVER) }
            VBox.setVgrow(CourseListScrollPane, Priority.ALWAYS)
        }

        val hBox = HBox(coursesVBox, CourseStatsTablePane).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            children.forEach { HBox.setHgrow(it, Priority.NEVER) }
            HBox.setHgrow(CourseStatsTablePane, Priority.ALWAYS)
        }
        val vBox = VBox(
            hBox, Separator(Orientation.HORIZONTAL),
            CourseStatsHBox
        ).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            children.forEach { VBox.setVgrow(it, Priority.NEVER) }
            VBox.setVgrow(hBox, Priority.ALWAYS)
        }

        val scene = Scene(vBox, 900.0, 450.0)
        stage.apply {
            title = "CS349 - A2 My Mark Visualization - l625zhan"
            this.scene = scene
            width = 900.0
            height = 450.0
            show()
        }
    }
}