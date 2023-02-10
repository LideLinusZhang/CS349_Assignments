package ca.uwaterloo.a1enhanced

import ca.uwaterloo.a1enhanced.controller.AddCourseHBox
import ca.uwaterloo.a1enhanced.controller.SortAndFilterCourseHBox
import ca.uwaterloo.a1enhanced.view.CourseListScrollPane
import ca.uwaterloo.a1enhanced.view.CourseStatsHBox
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.PopupControl.USE_PREF_SIZE
import javafx.scene.control.Separator
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MyMarkManagementApplication : Application() {
    override fun start(stage: Stage) {
        val vBox = VBox(
            SortAndFilterCourseHBox,
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

        with(stage) {
            title = "CS349 - A1 My Mark Management - l625zhan"
            this.scene = scene
            sizeToScene()
            icons.addAll(Image("icon.png"))
            show()
        }
    }
}