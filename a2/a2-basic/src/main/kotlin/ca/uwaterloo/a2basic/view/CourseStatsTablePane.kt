package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.view.tabs.DegreeProgressPlot
import ca.uwaterloo.a2basic.view.tabs.IncrementalTermAveragePlot
import ca.uwaterloo.a2basic.view.tabs.OutcomePieChart
import ca.uwaterloo.a2basic.view.tabs.TermAveragePlot
import javafx.geometry.Pos
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane

object CourseStatsTablePane : TabPane() {
    private val averagePlotStackPane =
        StackPane(TermAveragePlot.apply { CourseList.registerView(this) }).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            alignment = Pos.CENTER
        }

    private val degreeProgressPlotStackPane =
        StackPane(DegreeProgressPlot.apply { CourseList.registerView(this) }).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            alignment = Pos.CENTER
        }

    private val incrementalAveragePlotStackPane =
        StackPane(IncrementalTermAveragePlot.apply { CourseList.registerView(this) }).apply {
            maxWidth = Double.MAX_VALUE
            maxHeight = Double.MAX_VALUE
            alignment = Pos.CENTER
        }

    init {
        tabs.apply {
            add(Tab("Average by Term", averagePlotStackPane))
            add(Tab("Progress towards Degree", degreeProgressPlotStackPane))
            add(Tab("Course Outcome", OutcomePieChart))
            add(Tab("Incremental Average", incrementalAveragePlotStackPane))
        }

        tabClosingPolicy = TabClosingPolicy.UNAVAILABLE
        tabDragPolicy = TabDragPolicy.FIXED

        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
    }
}