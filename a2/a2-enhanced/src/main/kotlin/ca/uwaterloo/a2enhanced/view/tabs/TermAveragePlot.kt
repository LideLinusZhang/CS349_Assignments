package ca.uwaterloo.a2enhanced.view.tabs

import ca.uwaterloo.a2enhanced.model.Course
import ca.uwaterloo.a2enhanced.model.CourseList.coursesByTerm
import ca.uwaterloo.a2enhanced.model.enums.Term
import ca.uwaterloo.a2enhanced.plots.DotPlot
import ca.uwaterloo.a2enhanced.view.IView
import ca.uwaterloo.a2enhanced.view.extensions.toColor

object TermAveragePlot : DotPlot<Term, Double>(
    emptyArray(), (Course.scoreMin.toInt()..Course.scoreMax.toInt() step 10).map { it.toDouble() }.toTypedArray(),
    showXAxis = true, showYAxis = true,
    showHorizontalGridLine = true, showVerticalGridLine = true,
    xStartAtOrigin = false, yStartAtOrigin = true,
    margin = 40.0, dotRadius = 5.0, dotCircumferenceThickness = 1.5
), IView {
    init {
        draw()
    }

    override fun getCanvasYCoordinate(yCoordinate: Double): Double {
        return (height - 2.0 * margin) * (1.0 - yCoordinate / (Course.scoreMax - Course.scoreMin)) + margin
    }

    override fun update() {
        clear()

        val maxTermIndex = coursesByTerm.keys.maxOfOrNull { it.ordinal }
        val minTermIndex = coursesByTerm.keys.minOfOrNull { it.ordinal }

        setXAxisElements(
            if (maxTermIndex == null) emptyArray()
            else Term.values().copyOfRange(minTermIndex!!, maxTermIndex + 1)
        )

        coursesByTerm.forEach { (term, courses) ->
            val termAverage = courses.mapNotNull { it.scoreProperty.value }.average()
            addDataPoint(term, termAverage, termAverage.toColor())
        }

        draw()
    }
}