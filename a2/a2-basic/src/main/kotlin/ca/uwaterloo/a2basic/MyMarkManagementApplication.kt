package ca.uwaterloo.a2basic

import ca.uwaterloo.a2basic.controller.AddCourseHBox
import ca.uwaterloo.a2basic.view.AveragePlot
import ca.uwaterloo.a2basic.view.CourseListScrollPane
import ca.uwaterloo.a2basic.view.CourseStatsHBox
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.PopupControl.USE_PREF_SIZE
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MyMarkVisualizationApplication : Application() {
    override fun start(stage: Stage) {
        val coursesVBox = VBox(
            Separator(Orientation.HORIZONTAL),
            AddCourseHBox,
            Separator(Orientation.HORIZONTAL),
            CourseListScrollPane,
            Separator(Orientation.HORIZONTAL),
        ).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            children.forEach {
                VBox.setVgrow(it, Priority.NEVER)
            }
            VBox.setVgrow(CourseListScrollPane, Priority.ALWAYS)
        }
        val plotStackPane = StackPane(AveragePlot).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
        }
        val hBox = HBox(coursesVBox, plotStackPane).apply {
            maxWidth = Double.MAX_VALUE
            minHeight = USE_PREF_SIZE
            maxHeight = Double.MAX_VALUE
            HBox.setHgrow(coursesVBox, Priority.NEVER)
            HBox.setHgrow(plotStackPane, Priority.ALWAYS)
        }
        val vBox = VBox(hBox,Separator(Orientation.VERTICAL), CourseStatsHBox).apply {
            maxWidth = Double.MAX_VALUE
            minWidth = USE_PREF_SIZE
            maxHeight = Double.MAX_VALUE
            children.forEach {
                VBox.setVgrow(it, Priority.NEVER)
            }
            VBox.setVgrow(hBox, Priority.ALWAYS)
        }

        val scene = Scene(vBox, 900.0, 450.0)

        stage.title = "CS349 - A2 My Mark Visualization - l625zhan"
        stage.scene = scene
        stage.show()
    }
}