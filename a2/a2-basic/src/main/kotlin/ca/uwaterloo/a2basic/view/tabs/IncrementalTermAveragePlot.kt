package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.model.enums.Term
import ca.uwaterloo.a2basic.view.extensions.standardDeviation
import ca.uwaterloo.a2basic.view.extensions.toColor
import javafx.scene.paint.Color

class IncrementalTermAveragePlot(private val errorBarWidth: Double = 15.0) : TermAveragePlot() {
    private val errorBars = mutableMapOf<Pair<Term, Double>, Double>()

    private fun paintErrorBar(xCoordinate: Term, yCoordinate: Double, length: Double) {
        val xCanvasCoordinate = getCanvasXCoordinate(xCoordinate)
        val yCanvasCoordinate = getCanvasYCoordinate(yCoordinate)

        graphicsContext2D.apply {
            stroke = Color.BLACK

            strokeOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius * 2, dotRadius * 2)
            strokeLine(
                xCanvasCoordinate, yCanvasCoordinate + dotRadius - length / 2.0,
                xCanvasCoordinate, yCanvasCoordinate - dotRadius,
            )
            strokeLine(
                xCanvasCoordinate, yCanvasCoordinate - dotRadius + length / 2.0,
                xCanvasCoordinate, yCanvasCoordinate + dotRadius,
            )
            strokeLine(
                xCanvasCoordinate - errorBarWidth / 2.0, yCanvasCoordinate + dotRadius - length / 2.0,
                xCanvasCoordinate + errorBarWidth / 2.0, yCanvasCoordinate + dotRadius - length / 2.0
            )
            strokeLine(
                xCanvasCoordinate - errorBarWidth / 2.0, yCanvasCoordinate - dotRadius + length / 2.0,
                xCanvasCoordinate + errorBarWidth / 2.0, yCanvasCoordinate - dotRadius + length / 2.0
            )
        }
    }

    private fun addErrorBar(xCoordinate: Term, yCoordinate: Double, length: Double) {
        val coordinate = Pair(xCoordinate, yCoordinate)
        val exists = errorBars.containsKey(coordinate)
        val colorChanged = exists && errorBars[coordinate] != length

        if (!exists || colorChanged) {
            errorBars[coordinate] = length
            paintErrorBar(xCoordinate, yCoordinate, length)
        }
    }

    private fun clearAllErrorBars() {
        errorBars.clear()
        super.draw()
    }

    override fun draw() {
        super.draw()
        errorBars.forEach { (coordinate, color) -> addErrorBar(coordinate.first, coordinate.second, color) }
    }

    override fun update() {
        super.update()

        CourseList.courses.forEach {
            if (it.scoreProperty.value != null)
                addDataPoint(
                    it.termProperty.value,
                    it.scoreProperty.value!!.toDouble(),
                    it.scoreProperty.value!!.toColor(),
                    false
                )
        }

        clearAllErrorBars()
        CourseList.coursesByTerm.forEach { (term, courses) ->
            val sample = courses.mapNotNull { it.scoreProperty.value }
            val mean = sample.average()
            val sd = sample.standardDeviation()

            addErrorBar(term, mean, sd)
        }
    }
}