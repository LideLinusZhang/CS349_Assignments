package ca.uwaterloo.a2basic.plots

import javafx.scene.paint.Color

abstract class DotPlot<Tx, Ty>(
    xAxisElements: Array<Tx>, yAxisElements: Array<Ty>,
    showXAxis: Boolean = true,
    showYAxis: Boolean = true,

    showHorizontalGridLine: Boolean = true,
    showVerticalGridLine: Boolean = false,

    xStartAtOrigin: Boolean = false,
    yStartAtOrigin: Boolean = true,

    margin: Double = 40.0,

    private val dotRadius: Double = 7.5,
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
    private val dots = mutableListOf<Triple<Tx, Ty, Color>>()

    override fun setXAxisElements(xAxisElements: Array<Tx>) {
        super.setXAxisElements(xAxisElements)
        dots.clear()
    }

    override fun setYAxisElements(yAxisElements: Array<Ty>) {
        super.setYAxisElements(yAxisElements)
        dots.clear()
    }

    private fun paintDataPoint(xCoordinate: Tx, yCoordinate: Ty, color: Color) {
        val xCanvasCoordinate = getCanvasXCoordinate(xCoordinate)
        val yCanvasCoordinate = getCanvasYCoordinate(yCoordinate)

        graphicsContext2D.apply {
            stroke = color
            fill = color
            lineWidth = dotCircumferenceThickness
            strokeOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius, dotRadius)
            fillOval(xCanvasCoordinate - dotRadius, yCanvasCoordinate - dotRadius, dotRadius, dotRadius)
        }
    }

    fun addDataPoint(xCoordinate: Tx, yCoordinate: Ty, color: Color) {
        val existingIndex = dots.indexOfFirst { it.first == xCoordinate && it.second == yCoordinate }
        val exists = existingIndex != -1
        val colorChanged = exists && dots[existingIndex].third != color

        if (!exists || colorChanged) {
            if (colorChanged)
                dots.removeAt(existingIndex)

            dots.add(Triple(xCoordinate, yCoordinate, color))
            paintDataPoint(xCoordinate, yCoordinate, color)
        }
    }

    fun clearAllPoints() {
        dots.clear()
        super.draw()
    }

    override fun draw() {
        super.draw()
        dots.forEach {
            it.apply { paintDataPoint(first, second, third) }
        }
    }
}