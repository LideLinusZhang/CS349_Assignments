package ca.uwaterloo.a2enhanced.plots

import javafx.scene.paint.Color
import javafx.scene.shape.Arc
import javafx.scene.shape.ArcType

internal class Sector(startAngle: Double, centerAngle: Double, radius: Double) : Arc() {
    init {
        radiusX = radius
        radiusY = radius

        this.startAngle = startAngle
        this.length = centerAngle

        stroke = Color.BLACK
        type = ArcType.ROUND
    }
}