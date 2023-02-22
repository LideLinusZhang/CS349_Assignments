package ca.uwaterloo.a2enhanced.plots

import javafx.beans.binding.Bindings
import javafx.beans.binding.DoubleBinding
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import java.lang.Double.min

open class PieChart : StackPane() {
    private val elements = mutableListOf<Pair<Color, String>>()
    private val sizeProperty: DoubleBinding =
        Bindings.createDoubleBinding({ width * height }, widthProperty(), heightProperty())

    init {
        maxHeight = Double.MAX_VALUE
        maxWidth = Double.MAX_VALUE
        minHeight = 1.0
        minWidth = 1.0
        alignment = Pos.CENTER
        sizeProperty.addListener { _, _, _ -> children.clear(); draw() }
        draw()
    }

    fun addElement(color: Color, description: String) {
        elements.add(Pair(color, description))
    }

    fun clear() {
        children.clear()
        elements.clear()
    }

    fun draw() {
        val pie = Group()
        val details = Label().apply {
            prefWidth = this@PieChart.width
            prefHeight = this@PieChart.height
            textAlignment = TextAlignment.LEFT
            alignment = Pos.TOP_LEFT
        }

        val radius = min(width, height) / 2.0 * 0.95

        var startAngle = 0.0
        elements.groupBy { it.first }.forEach { (color, descriptions) ->
            val angle = 360.0 * (descriptions.size.toDouble() / elements.size.toDouble())
            val combinedDescription = StringBuilder().apply {
                descriptions.forEach { this.appendLine(it.second) }
            }.toString()

            val sector = Sector(startAngle, min(angle, 360.0 - startAngle), radius).apply {
                fill = color
                onMouseEntered = EventHandler { details.text = combinedDescription }
                onMouseExited = EventHandler { details.text = "" }
            }
            startAngle += angle

            pie.children.add(sector)
        }

        children.addAll(details, pie)
        setAlignment(details, Pos.TOP_LEFT)
    }
}