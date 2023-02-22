package ca.uwaterloo.a2basic.plots

import javafx.scene.shape.Arc

internal class Sector(startAngle: Double, centerAngle: Double, radius: Double) : Arc() {
    init {
        radiusX = radius
        radiusY = radius

        this.startAngle = startAngle
        this.length = centerAngle
    }
}