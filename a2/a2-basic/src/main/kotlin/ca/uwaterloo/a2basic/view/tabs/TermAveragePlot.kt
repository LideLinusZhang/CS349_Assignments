package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.Course
import ca.uwaterloo.a2basic.model.CourseList.coursesByTerm
import ca.uwaterloo.a2basic.model.enums.Term
import ca.uwaterloo.a2basic.plots.DotPlot
import ca.uwaterloo.a2basic.view.IView
import ca.uwaterloo.a2basic.view.extensions.toColor

open class TermAveragePlot(
    yAxisElements: Array<Double> = (Course.scoreMin.toInt()..Course.scoreMax.toInt() step 10)
        .map { it.toDouble() }.toTypedArray()
) : DotPlot<Term, Double>(emptyArray(), yAxisElements), IView {
    private var previousMaxTermIndex: Int? = null
    private var previousMinTermIndex: Int? = null

    init {
        super.draw()
    }

    override fun getCanvasYCoordinate(yCoordinate: Double): Double {
        return (height - 2.0 * margin) * (1.0 - yCoordinate / (Course.scoreMax - Course.scoreMin)) + margin
    }

    override fun update() {
        val maxTermIndex = coursesByTerm.keys.maxOfOrNull { it.ordinal }
        val minTermIndex = coursesByTerm.keys.minOfOrNull { it.ordinal }

        if (previousMaxTermIndex != maxTermIndex || previousMinTermIndex != minTermIndex) {
            setXAxisElements(
                if (maxTermIndex == null)
                    emptyArray()
                else
                    Term.values().copyOfRange(minTermIndex!!, maxTermIndex + 1)
            )
            previousMaxTermIndex = maxTermIndex
            previousMinTermIndex = minTermIndex
        } else clearAllPoints()

        coursesByTerm.keys.forEach { term ->
            val termAverage = coursesByTerm.getValue(term).mapNotNull { it.scoreProperty.value }.average()

            addDataPoint(term, termAverage, termAverage.toColor())
        }
    }
}