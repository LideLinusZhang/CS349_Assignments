package ca.uwaterloo.a2basic.plots

import javafx.scene.paint.Color

abstract class DotPlot<Tx, Ty>(
    xAxisElements: Array<Tx>, yAxisElements: Array<Ty>,
    showXAxis: Boolean = true,
    showYAxis: Boolean = true,

    showHorizontalGridLine: Boolean = true,
    showVerticalGridLine: Boolean = true,

    xStartAtOrigin: Boolean = false,
    yStartAtOrigin: Boolean = true,

    margin: Double = 40.0,

    protected val dotRadius: Double = 5.0,
    private val dotCircumferenceThickness: Double = 1.5
) :
    XAndYAxes<Tx, Ty>(
        xAxisElements,
        yAxisElements,
        showXAxis,
        showYAxis,
        showHorizontalGridLine,
        showVerticalGridLine,
        xStartAtOrigin,
        yStartAtOrigin,
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

            if (!exists || colorChanged) {
                this[coordinate] = color
                paintDataPoint(xCoordinate, yCoordinate, color)
            }
        }
    }

    fun clearAllPoints() {
        filledDots.clear()
        hollowDots.clear()
        super.draw()
    }

    override fun draw() {
        super.draw()
        filledDots.forEach { (coordinate, color) -> paintDataPoint(coordinate.first, coordinate.second, color) }
        hollowDots.forEach { (coordinate, color) -> paintDataPoint(coordinate.first, coordinate.second, color, false) }
    }
}