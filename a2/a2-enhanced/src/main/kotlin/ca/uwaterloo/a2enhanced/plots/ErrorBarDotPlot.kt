package ca.uwaterloo.a2enhanced.plots

import javafx.scene.paint.Color

abstract class ErrorBarDotPlot<Tx, Ty>(
    xAxisElements: Array<Tx>, yAxisElements: Array<Ty>,
    showXAxis: Boolean, showYAxis: Boolean,
    showHorizontalGridLine: Boolean, showVerticalGridLine: Boolean,
    xStartAtOrigin: Boolean, yStartAtOrigin: Boolean,
    margin: Double, dotRadius: Double, dotCircumferenceThickness: Double,
    private val errorBarWidth: Double
) : DotPlot<Tx, Ty>(
    xAxisElements, yAxisElements,
    showXAxis, showYAxis,
    showHorizontalGridLine, showVerticalGridLine,
    xStartAtOrigin, yStartAtOrigin,
    margin, dotRadius, dotCircumferenceThickness
) {
    private val errorBars = mutableMapOf<Pair<Tx, Ty>, Double>()

    private fun paintErrorBar(xCoordinate: Tx, yCoordinate: Ty, canvasLength: Double) {
        val xCanvasCoordinate = getCanvasXCoordinate(xCoordinate)
        val yCanvasCoordinate = getCanvasYCoordinate(yCoordinate)

        graphicsContext2D.apply {
            stroke = Color.BLACK

            strokeOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius * 2, dotRadius * 2)
            strokeLine(
                xCanvasCoordinate, yCanvasCoordinate + dotRadius - canvasLength / 2.0,
                xCanvasCoordinate, yCanvasCoordinate - dotRadius,
            )
            strokeLine(
                xCanvasCoordinate, yCanvasCoordinate - dotRadius + canvasLength / 2.0,
                xCanvasCoordinate, yCanvasCoordinate + dotRadius,
            )
            strokeLine(
                xCanvasCoordinate - errorBarWidth / 2.0, yCanvasCoordinate + dotRadius - canvasLength / 2.0,
                xCanvasCoordinate + errorBarWidth / 2.0, yCanvasCoordinate + dotRadius - canvasLength / 2.0
            )
            strokeLine(
                xCanvasCoordinate - errorBarWidth / 2.0, yCanvasCoordinate - dotRadius + canvasLength / 2.0,
                xCanvasCoordinate + errorBarWidth / 2.0, yCanvasCoordinate - dotRadius + canvasLength / 2.0
            )
        }
    }

    fun addErrorBar(xCoordinate: Tx, yCoordinate: Ty, canvasLength: Double) {
        val coordinate = Pair(xCoordinate, yCoordinate)
        val exists = errorBars.containsKey(coordinate)
        val colorChanged = exists && errorBars[coordinate] != canvasLength

        if (!exists || colorChanged)
            errorBars[coordinate] = canvasLength
    }

    override fun clear() {
        super.clear()
        errorBars.clear()
    }

    override fun draw() {
        super.draw()
        errorBars.forEach { (coordinate, color) -> paintErrorBar(coordinate.first, coordinate.second, color) }
    }
}