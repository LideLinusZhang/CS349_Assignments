package ca.uwaterloo.a2enhanced.plots

import javafx.scene.paint.Color

abstract class Histogram<Tx, Ty>(
    xAxisElements: Array<Tx>, yAxisElements: Array<Ty>,
    showXAxis: Boolean, showYAxis: Boolean,
    showHorizontalGridLine: Boolean, showVerticalGridLine: Boolean,
    xStartAtOrigin: Boolean, yStartAtOrigin: Boolean,
    margin: Double
) : XAndYAxes<Tx, Ty>(
    xAxisElements, yAxisElements,
    showXAxis, showYAxis,
    showHorizontalGridLine, showVerticalGridLine,
    xStartAtOrigin, yStartAtOrigin,
    margin
) {
    private val horizontalBars = mutableMapOf<Triple<Tx, Ty, Int>, Color>()
    private val verticalBars = mutableMapOf<Triple<Tx, Ty, Int>, Color>()
    private val barWidth: Double get() = yStepProperty.value.toDouble() / 2.0

    private fun paintHorizontalBar(xCoordinate: Tx, yCoordinate: Ty, color: Color) {
        val xCanvasCoordinate = getCanvasXCoordinate(xCoordinate)
        val yCanvasCoordinate = getCanvasYCoordinate(yCoordinate)

        graphicsContext2D.apply {
            fill = color
            fillRect(margin, yCanvasCoordinate - barWidth / 2.0, xCanvasCoordinate - margin, barWidth)
        }
    }

    fun addBar(xCoordinate: Tx, yCoordinate: Ty, color: Color, level: Int, horizontal: Boolean = true) {
        (if (horizontal) horizontalBars else verticalBars)
            .apply {
                val coordinate = Triple(xCoordinate, yCoordinate, level)
                val exists = this.containsKey(coordinate)
                val colorChanged = exists && this[coordinate] != color

                if (!exists || colorChanged)
                    this[coordinate] = color
            }
    }

    override fun clear() {
        super.clear()
        horizontalBars.clear()
        verticalBars.clear()
    }

    override fun draw() {
        horizontalBars.keys.sortedBy { it.third }.forEach { coordinate ->
            paintHorizontalBar(coordinate.first, coordinate.second, horizontalBars[coordinate]!!)
        }

        super.draw()
    }
}