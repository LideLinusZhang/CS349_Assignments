package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.Course
import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.model.enums.Term
import ca.uwaterloo.a2basic.plots.ErrorBarDotPlot
import ca.uwaterloo.a2basic.view.IView
import ca.uwaterloo.a2basic.view.extensions.standardDeviation
import ca.uwaterloo.a2basic.view.extensions.toColor

object IncrementalTermAveragePlot : ErrorBarDotPlot<Term, Double>(
    emptyArray(), (Course.scoreMin.toInt()..Course.scoreMax.toInt() step 10).map { it.toDouble() }.toTypedArray(),
    showXAxis = true, showYAxis = true,
    showHorizontalGridLine = true, showVerticalGridLine = true,
    xStartAtOrigin = false, yStartAtOrigin = true,
    margin = 40.0, dotRadius = 5.0, dotCircumferenceThickness = 1.5, errorBarWidth = 12.5
), IView {
    init {
        draw()
    }

    private fun getCanvasLength(length: Double): Double {
        return (height - 2.0 * margin) * (length / (Course.scoreMax - Course.scoreMin))
    }

    override fun getCanvasYCoordinate(yCoordinate: Double): Double {
        return (height - 2.0 * margin) * (1.0 - yCoordinate / (Course.scoreMax - Course.scoreMin)) + margin
    }

    override fun update() {
        clear()

        val maxTermIndex = CourseList.coursesByTerm.keys.maxOfOrNull { it.ordinal }
        val minTermIndex = CourseList.coursesByTerm.keys.minOfOrNull { it.ordinal }

        setXAxisElements(
            if (maxTermIndex == null) emptyArray()
            else Term.values().copyOfRange(minTermIndex!!, maxTermIndex + 1)
        )

        CourseList.courses.forEach {
            if (it.scoreProperty.value != null)
                addDataPoint(
                    it.termProperty.value,
                    it.scoreProperty.value!!.toDouble(),
                    it.scoreProperty.value!!.toColor(),
                    fillPoint = false
                )
        }

        CourseList.coursesByTerm.forEach { (term, courses) ->
            val sample = courses.mapNotNull { it.scoreProperty.value }
            val mean = sample.average()
            val sd = sample.standardDeviation()

            addDataPoint(term, mean, mean.toColor())
            addErrorBar(term, mean, getCanvasLength(sd))
        }

        draw()
    }
}