package ca.uwaterloo.a2basic.plots

import javafx.beans.binding.Bindings
import javafx.beans.binding.DoubleBinding
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.geometry.VPos
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

abstract class XAndYAxes<Tx, Ty>(
    xAxisElements: Array<Tx>,
    yAxisElements: Array<Ty>,

    private val showXAxis: Boolean,
    private val showYAxis: Boolean,

    private val showHorizontalGridLine: Boolean,
    private val showVerticalGridLine: Boolean,

    private val xStartAtOrigin: Boolean,
    private val yStartAtOrigin: Boolean,

    protected val margin: Double
) : ResizableCanvas() {
    private val xAxisElementsProperty =
        SimpleListProperty(FXCollections.observableList(xAxisElements.toMutableList()))
    private val yAxisElementsProperty =
        SimpleListProperty(FXCollections.observableList(yAxisElements.reversed().toMutableList()))

    private val xStepProperty =
        Bindings.divide(
            Bindings.subtract(widthProperty(), 2.0 * margin),
            if (xStartAtOrigin)
                xAxisElementsProperty.sizeProperty()
            else
                Bindings.add(xAxisElementsProperty.sizeProperty(), 1)
        )
    private val yStepProperty =
        Bindings.divide(
            Bindings.subtract(heightProperty(), 2.0 * margin),
            if (yStartAtOrigin)
                yAxisElementsProperty.sizeProperty()
            else
                Bindings.add(yAxisElementsProperty.sizeProperty(), 1)
        )

    protected val sizeProperty: DoubleBinding =
        Bindings.createDoubleBinding({ width * height }, widthProperty(), heightProperty())

    init {
        yAxisElements.reverse()
        sizeProperty.addListener { _, _, _ -> this.draw() }
    }

    protected open fun setXAxisElements(xAxisElements: Array<Tx>) {
        xAxisElementsProperty.setAll(xAxisElements.toMutableList())
        this.draw()
    }

    protected open fun setYAxisElements(yAxisElements: Array<Ty>) {
        yAxisElementsProperty.setAll(yAxisElements.toMutableList())
        this.draw()
    }

    protected open fun getCanvasXCoordinate(xCoordinate: Tx): Double {
        return margin + (xAxisElementsProperty.indexOf(xCoordinate) + if (xStartAtOrigin) 0 else 1) * xStepProperty.doubleValue()
    }

    protected open fun getCanvasYCoordinate(yCoordinate: Ty): Double {
        return margin + (yAxisElementsProperty.indexOf(yCoordinate) + if (yStartAtOrigin) 0 else 1) * yStepProperty.doubleValue()
    }

    private fun paintXAxis() {
        graphicsContext2D.apply {
            stroke = Color.BLACK
            fill = Color.BLACK
            strokeLine(margin, height - margin, width - margin, height - margin)

            textAlign = TextAlignment.CENTER
            textBaseline = VPos.TOP
            xAxisElementsProperty.forEach {
                fillText(it.toString(), getCanvasXCoordinate(it), height - margin)
            }
        }
    }

    private fun paintYAxis() {
        graphicsContext2D.apply {
            stroke = Color.BLACK
            fill = Color.BLACK
            strokeLine(margin, margin, margin, height - margin)

            textAlign = TextAlignment.RIGHT
            textBaseline = VPos.BASELINE
            yAxisElementsProperty.forEach {
                fillText(it.toString(), margin - 5.0, getCanvasYCoordinate(it))
            }
        }
    }

    private fun paintVerticalGridLines() {
        graphicsContext2D.apply {
            stroke = Color.LIGHTGRAY
            xAxisElementsProperty.forEach {
                val xCoordinate = getCanvasXCoordinate(it)
                strokeLine(xCoordinate, margin, xCoordinate, height - margin)
            }
        }
    }

    private fun paintHorizontalGridLines() {
        graphicsContext2D.apply {
            stroke = Color.LIGHTGRAY
            yAxisElementsProperty.forEach {
                val yCoordinate = getCanvasYCoordinate(it)
                strokeLine(margin, yCoordinate, width - margin, yCoordinate)
            }
        }
    }

    open fun draw() {
        graphicsContext2D.apply {
            clearRect(0.0, 0.0, width, height)

            if (showVerticalGridLine)
                paintVerticalGridLines()
            if (showHorizontalGridLine)
                paintHorizontalGridLines()
            if (showXAxis)
                paintXAxis()
            if (showYAxis)
                paintYAxis()
        }
    }
}