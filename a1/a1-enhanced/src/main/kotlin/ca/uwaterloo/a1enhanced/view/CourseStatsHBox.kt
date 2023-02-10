package ca.uwaterloo.a1enhanced.view

import ca.uwaterloo.a1enhanced.model.CourseList
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

object CourseStatsHBox : HBox(), IView {
    private val averageLabel = StatsLabel()
    private val medianLabel = StatsLabel()
    private val countLabel = StatsLabel()
    private val failedCountLabel = StatsLabel()
    private val wdSeparator = Separator(Orientation.VERTICAL)
    private val wdCountLabel = StatsLabel()

    init {
        maxWidth = Double.MAX_VALUE
        minWidth = USE_PREF_SIZE
        children.addAll(
            averageLabel,
            Separator(Orientation.VERTICAL),
            medianLabel,
            Separator(Orientation.VERTICAL),
            countLabel,
            Separator(Orientation.VERTICAL),
            failedCountLabel,
            wdSeparator,
            wdCountLabel
        )
        children.forEach {
            setHgrow(it, Priority.NEVER)
            if (it is Label) setMargin(it, Insets(5.0, 5.0, 5.0, 5.0))
        }

        update()
        CourseList.registerView(this)
    }

    override fun update() {
        with(CourseList) {
            averageLabel.text =
                "Course Average: ${String.format("%.2f", if (courseAverage.isNaN()) 0.0 else courseAverage)}"
            medianLabel.text =
                "Course Median: ${String.format("%.2f", if (courseMedian.isNaN()) 0.0 else courseMedian)}"
            countLabel.text = "Courses Taken: $courseCount"
            failedCountLabel.text = "Courses Failed: $courseFailedCount"
            wdCountLabel.text = "Courses WD'ed: $courseWDCount"

            wdSeparator.isVisible = includeWD
            wdCountLabel.isVisible = includeWD
        }
    }
}