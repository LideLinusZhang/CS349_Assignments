package ca.uwaterloo.a2enhanced.plots

import javafx.scene.paint.Color

abstract class DotPlot<Tx, Ty>(
    xAxisElements: Array<Tx>, yAxisElements: Array<Ty>,
    showXAxis: Boolean, showYAxis: Boolean,
    showHorizontalGridLine: Boolean, showVerticalGridLine: Boolean,
    xStartAtOrigin: Boolean, yStartAtOrigin: Boolean,
    margin: Double, protected val dotRadius: Double, private val dotCircumferenceThickness: Double
) :
    XAndYAxes<Tx, Ty>(
        xAxisElements, yAxisElements,
        showXAxis, showYAxis,
        showHorizontalGridLine, showVerticalGridLine,
        xStartAtOrigin, yStartAtOrigin,
        margin
    ) {
    private val filledDots = mutableMapOf<Pair<Tx, Ty>, Color>()
    private val hollowDots = mutableMapOf<Pair<Tx, Ty>, Color>()

    override fun setXAxisElements(xAxisElements: Array<Tx>) {
        super.setXAxisElements(xAxisElements)
        filledDots.clear()
        hollowDots.clear()
    }

    override fun setYAxisElements(yAxisElements: Array<Ty>) {
        super.setYAxisElements(yAxisElements)
        filledDots.clear()
        hollowDots.clear()
    }

    private fun paintDataPoint(xCoordinate: Tx, yCoordinate: Ty, color: Color, fillPoint: Boolean = true) {
        val xCanvasCoordinate = getCanvasXCoordinate(xCoordinate)
        val yCanvasCoordinate = getCanvasYCoordinate(yCoordinate)

        graphicsContext2D.apply {
            stroke = color
            fill = color
            lineWidth = dotCircumferenceThickness
            strokeOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius * 2, dotRadius * 2)
            if (fillPoint)
                fillOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius * 2, dotRadius * 2)
        }
    }

    fun addDataPoint(xCoordinate: Tx, yCoordinate: Ty, color: Color, fillPoint: Boolean = true) {
        (if (fillPoint) filledDots else hollowDots).apply {
            val coordinate = Pair(xCoordinate, yCoordinate)
            val exists = this.containsKey(coordinate)
            val colorChanged = exists && this[coordinate] != color

            if (!exists || colorChanged)
                this[coordinate] = color
        }
    }

    override fun clear() {
        super.clear()
        filledDots.clear()
        hollowDots.clear()
    }

    override fun draw() {
        super.draw()
        filledDots.forEach { (coordinate, color) -> paintDataPoint(coordinate.first, coordinate.second, color) }
        hollowDots.forEach { (coordinate, color) -> paintDataPoint(coordinate.first, coordinate.second, color, false) }
    }
}