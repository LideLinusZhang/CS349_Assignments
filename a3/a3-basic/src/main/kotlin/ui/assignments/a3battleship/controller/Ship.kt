package ui.assignments.a3battleship.controller

import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.animation.TranslateTransition
import javafx.event.EventHandler
import javafx.geometry.VPos
import javafx.scene.Group
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import ui.assignments.a3battleship.model.Cell
import ui.assignments.a3battleship.model.Orientation
import ui.assignments.a3battleship.model.ShipType

class Ship(private val playerBoard: PlayerBoard, private val shipType: ShipType) : Group() {
    private var inMoving: Boolean = false
    private var orientation: Orientation = Orientation.Vertical

    private lateinit var moveInfo: MoveInfo

    private val width = playerBoard.gridSize * 0.5
    private val height = playerBoard.gridSize * shipType.length()

    private val shape = Rectangle(width, height, Color.AZURE).apply { stroke = Color.BLACK; strokeWidth = 1.0 }
    private val text = Text(shipType.abbreviation()).apply {
        textAlignment = TextAlignment.CENTER
        textOrigin = VPos.CENTER
        rotate = 90.0
    }

    private var shipId: Int = Cell.NoShip

    init {
        children.addAll(shape, text)

        onMouseClicked = EventHandler {
            if (it.button == MouseButton.PRIMARY) {
                if (inMoving) endMoving()
                else {
                    startMoving()
                    updateTranslation(it.sceneX, it.sceneY)
                }
            } else if (inMoving) rotate()
        }

        addEventFilter(MouseEvent.MOUSE_MOVED) {
            if (inMoving) updateTranslation(it.sceneX, it.sceneY)
        }
    }

    private fun rotate() {
        RotateTransition(Duration.seconds(1.0), this).apply {
            toAngle = if (orientation == Orientation.Vertical) {
                orientation = Orientation.Horizontal
                90.0
            } else {
                orientation = Orientation.Vertical
                0.0
            }
            interpolator = Interpolator.EASE_BOTH
            isAutoReverse = true
            cycleCount = 1
        }.play()
    }

    private fun updateTranslation(mouseSceneX: Double, mouseSceneY: Double) {
        translateX = moveInfo.initialX + mouseSceneX - moveInfo.anchorX
        translateY = moveInfo.initialY + mouseSceneY - moveInfo.anchorY
    }

    private fun startMoving() {
        inMoving = true
        viewOrder -= 1000

        val sceneCoordinate = localToScene(0.0, 0.0)
        moveInfo =
            MoveInfo((sceneCoordinate.x + 0.5 * width), (sceneCoordinate.y + 0.5 * height), translateX, translateY)
    }

    private fun endMoving() {
        inMoving = false
        viewOrder += 1000

        val sceneCoordinate = localToScene(0.0, 0.0)

        if (playerBoard.isInBoard(sceneCoordinate.x, sceneCoordinate.y, width, height)) {
            val result = if (orientation == Orientation.Vertical) {
                val bowX = sceneCoordinate.x + 0.5 * width

                playerBoard.placeBattleship(shipType, orientation, bowX, sceneCoordinate.y)
            } else {
                val bowX = sceneCoordinate.x + height
                val bowY = sceneCoordinate.y + 0.5 * width

                playerBoard.placeBattleship(shipType, orientation, bowX, bowY)
            }

            if (result.shipId != Cell.NoShip) {
                shipId = result.shipId

                if (orientation == Orientation.Vertical) {
                    updateTranslation(result.normalizedLayoutX, result.normalizedLayoutY + 0.5 * height)
                } else {
                    layoutX = result.normalizedLayoutX - height
                    layoutY = result.normalizedLayoutY - 0.5 * width
                }

                return
            }
        }

        backToInitialPosition()
    }

    private fun backToInitialPosition() {
        TranslateTransition(Duration.seconds(1.0), this).apply {
            byX = -translateX
            byY = -translateY
            interpolator = Interpolator.EASE_BOTH
            isAutoReverse = true
            cycleCount = 1
        }.play()

        if (orientation == Orientation.Horizontal) {
            orientation = Orientation.Vertical

            RotateTransition(Duration.seconds(1.0), this).apply {
                toAngle = 0.0
                interpolator = Interpolator.EASE_BOTH
                isAutoReverse = true
                cycleCount = 1
            }.play()
        }
    }
}