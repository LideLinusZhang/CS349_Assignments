package ui.assignments.a3battleship.view

import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.Player
import kotlin.math.floor

abstract class Board(
    size: Double = 300.0,
    private val dimension: Int = 10,
    private val margin: Double = 25.0,
    protected val game: Game,
    protected val player: Player
) : Canvas(size, size), IView {
    private val sizeWithoutMargin: Double = size - 2.0 * margin
    protected val sceneX: Double get() = localToScene(0.0, 0.0).x
    protected val sceneY: Double get() = localToScene(0.0, 0.0).y
    val gridSize: Double = sizeWithoutMargin / dimension


    init {
        draw()
    }

    protected fun getCanvasCoordinate(boardCoordinate: Int): Double = margin + boardCoordinate * gridSize
    protected fun getBoardCoordinate(canvasCoordinate: Double): Int =
        floor((canvasCoordinate - margin) / gridSize).toInt()

    private fun paintGrids() {
        val board = game.getBoard(player)

        for (y in 0 until dimension) {
            for (x in 0 until dimension) {
                val canvasXCoordinate = getCanvasCoordinate(x)
                val canvasYCoordinate = getCanvasCoordinate(y)
                val cell = board[y][x]

                graphicsContext2D.apply {
                    fill = cell.toColor()
                    stroke = Color.BLACK
                    lineWidth = 1.5
                    fillRect(canvasXCoordinate, canvasYCoordinate, gridSize, gridSize)
                    strokeRect(canvasXCoordinate, canvasYCoordinate, gridSize, gridSize)
                }
            }
        }
    }

    private fun paintXCoordinates() {
        val canvasYCoordinate = margin * 0.5

        graphicsContext2D.apply {
            textBaseline = VPos.CENTER
            textAlign = TextAlignment.CENTER
            lineWidth = 1.0
            fill = Color.BLACK

            for (x in 0 until dimension) {
                val canvasXCoordinate = getCanvasCoordinate(x) + 0.5 * gridSize

                fillText((x + 1).toString(), canvasXCoordinate, canvasYCoordinate)
            }
        }
    }

    private fun paintYCoordinates() {
        val canvasXCoordinate = margin * 0.5

        graphicsContext2D.apply {
            textBaseline = VPos.CENTER
            textAlign = TextAlignment.CENTER
            lineWidth = 1.0
            fill = Color.BLACK

            for (y in 0 until dimension) {
                val canvasYCoordinate = getCanvasCoordinate(y) + 0.5 * gridSize

                fillText(('A' + y).toString() + " ", canvasXCoordinate, canvasYCoordinate)
            }
        }
    }

    private fun draw() {
        clearCanvas()
        paintGrids()
        paintXCoordinates()
        paintYCoordinates()
    }

    private fun clearCanvas() {
        graphicsContext2D.clearRect(0.0, 0.0, width, height)
    }

    fun isInBoard(sceneX: Double, sceneY: Double): Boolean {
        val min = localToScene(margin, margin)
        val max = localToScene(width - margin, height - margin)

        return min.x < sceneX && sceneX < max.x && min.y < sceneY && sceneY < max.y
    }

    override fun update() {
        draw()
    }
}