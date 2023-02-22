package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.view.tabs.*
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane

object CourseStatsTablePane : TabPane() {
    private val averagePlotStackPane = StackPane(TermAveragePlot)
    private val degreeProgressPlotStackPane = TabStackPane(DegreeProgressPlot)
    private val incrementalAveragePlotStackPane = StackPane(IncrementalTermAveragePlot)

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
        minWidth = 1.0
        minHeight = 1.0
    }
}