package ca.uwaterloo.a2enhanced.plots

import javafx.scene.canvas.Canvas

open class ResizableCanvas : Canvas() {
    override fun isResizable(): Boolean = true

    override fun maxWidth(width: Double): Double {
        return Double.MAX_VALUE
    }

    override fun maxHeight(height: Double): Double {
        return Double.MAX_VALUE
    }

    override fun minWidth(height: Double): Double {
        return 1.0
    }

    override fun minHeight(width: Double): Double {
        return 1.0
    }

    override fun resize(width: Double, height: Double) {
        this.width = width
        this.height = height
    }

    fun clearCanvas() {
        this.graphicsContext2D.clearRect(0.0, 0.0, width, height)
    }
}