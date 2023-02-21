package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.model.enums.CourseType
import ca.uwaterloo.a2basic.plots.Histogram
import ca.uwaterloo.a2basic.view.IView
import ca.uwaterloo.a2basic.view.extensions.toColor

object DegreeProgressPlot : Histogram<Double, CourseType>(
    (0..CourseType.All.creditRequired() step 5).map { it.toDouble() }.toTypedArray(),
    CourseType.values().reversedArray(),
    showXAxis = false, showYAxis = true,
    showVerticalGridLine = true, showHorizontalGridLine = false,
    xStartAtOrigin = true, yStartAtOrigin = false,
    margin = 40.0, barWidth = 15.0
), IView {
    init {
        draw()
    }

    override fun getCanvasXCoordinate(xCoordinate: Double): Double {
        return (width - 2.0 * margin) * (xCoordinate / CourseType.All.creditRequired()) + margin
    }

    private fun addRequiredCreditBars() {
        CourseType.values().forEach {
            addBar(it.creditRequired().toDouble(), it, it.toColor(lightColor = true), 0)
        }
    }

    override fun draw() {
        addRequiredCreditBars()
        super.draw()
    }

    override fun update() {
        clear()

        CourseType.values().forEach {
            val passed: Int =
                if (it == CourseType.All) {
                    CourseList.courses
                } else {
                    CourseList.coursesByType[it]
                }?.count { course -> !(course.isFailed || course.isWD) } ?: 0

            addBar(passed * 0.5, it, it.toColor(), 1)
        }

        draw()
    }
}