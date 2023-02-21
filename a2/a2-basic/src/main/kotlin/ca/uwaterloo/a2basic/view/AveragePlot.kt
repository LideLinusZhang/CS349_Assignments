package ca.uwaterloo.a2basic.view

import ca.uwaterloo.a2basic.model.Course
import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.model.Term
import ca.uwaterloo.a2basic.plots.DotPlot

private val yAxisElements = (Course.scoreMin.toInt()..Course.scoreMax.toInt() step 10)
    .map { it.toDouble() }.toTypedArray()

object AveragePlot : DotPlot<Term, Double>(emptyArray(), yAxisElements), IView {
    private var previousMaxTermIndex: Int? = null
    private var previousMinTermIndex: Int? = null

    init {
        CourseList.registerView(this)
        draw()
    }

    override fun getCanvasYCoordinate(yCoordinate: Double): Double {
        return (height - 2.0 * margin) * (1.0 - yCoordinate / (Course.scoreMax - Course.scoreMin)) + margin
    }

    override fun update() {
        val groupedByTerm = CourseList.courses.groupBy { it.termProperty.value }
        val maxTermIndex = groupedByTerm.keys.maxOfOrNull { it.ordinal }
        val minTermIndex = groupedByTerm.keys.minOfOrNull { it.ordinal }

        if (previousMaxTermIndex != maxTermIndex || previousMinTermIndex != minTermIndex) {
            setXAxisElements(
                if (maxTermIndex == null)
                    emptyArray()
                else
                    Term.values().copyOfRange(minTermIndex!!, maxTermIndex + 1)
            )
        } else clearAllPoints()

        groupedByTerm.keys.forEach {
            val termAverage = groupedByTerm.getValue(it).mapNotNull { it.scoreProperty.value }.average()

            addDataPoint(it, termAverage, termAverage.toColor())
        }
    }
}