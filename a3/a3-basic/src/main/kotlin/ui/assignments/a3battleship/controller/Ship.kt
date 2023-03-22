package ui.assignments.a3battleship.controller

import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.animation.TranslateTransition
import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.beans.property.SimpleIntegerProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import ui.assignments.a3battleship.model.Cell
import ui.assignments.a3battleship.model.Orientation
import ui.assignments.a3battleship.model.ShipType

class Ship(private val playerBoard: PlayerBoard, private val shipType: ShipType) : StackPane() {
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

    private val shipIdProperty = SimpleIntegerProperty(Cell.NoShip)
    var shipId: Int
        get() = shipIdProperty.value
        set(value) {
            shipIdProperty.value = value
        }

    val shipPlacedProperty: BooleanBinding = Bindings.notEqual(Cell.NoShip, shipIdProperty)
    var isMoveEnabled: Boolean = true

    init {
        children.addAll(shape, text)
        alignment = Pos.CENTER
        prefHeight = height
        prefWidth = width

        shape.onMouseClicked = EventHandler { handleMouseClick(it) }
        text.onMouseClicked = EventHandler { handleMouseClick(it) }

        addEventFilter(MouseEvent.MOUSE_MOVED) {
            if (inMoving) updateTranslation(it.sceneX, it.sceneY)
        }
    }

    private fun handleMouseClick(mouseEvent: MouseEvent) {
        if (isMoveEnabled) {
            if (mouseEvent.button == MouseButton.PRIMARY) {
                if (inMoving) endMoving()
                else {
                    startMoving()
                    updateTranslation(mouseEvent.sceneX, mouseEvent.sceneY)
                }
            } else if (inMoving) rotate()
        }
    }

    private fun rotate() {
        RotateTransition(Duration.seconds(1.0), this).apply {
            toAngle = if (orientation == Orientation.Vertical) {
                orientation = Orientation.Horizontal
                -90.0
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
        if (shipId != Cell.NoShip) {
            playerBoard.removeBattleship(shipId)
            shipId = Cell.NoShip
        }

        inMoving = true
        viewOrder -= 1000

        val sceneCoordinate = shape.localToScene(0.0, 0.0)
        moveInfo = if (orientation == Orientation.Vertical)
            MoveInfo((sceneCoordinate.x + 0.5 * width), (sceneCoordinate.y + 0.5 * height), translateX, translateY)
        else
            MoveInfo((sceneCoordinate.x + 0.5 * height), (sceneCoordinate.y - 0.5 * width), translateX, translateY)
    }

    private fun endMoving() {
        inMoving = false
        viewOrder += 1000

        val sceneCoordinate = shape.localToScene(0.0, 0.0)

        if (playerBoard.isInBoard(sceneCoordinate.x, sceneCoordinate.y)) {
            val result = if (orientation == Orientation.Vertical) {
                val bowSceneXCoordinate = sceneCoordinate.x + 0.5 * width

                playerBoard.placeBattleship(shipType, orientation, bowSceneXCoordinate, sceneCoordinate.y)
            } else {
                val bowSceneYCoordinate = sceneCoordinate.y - 0.5 * width

                playerBoard.placeBattleship(shipType, orientation, sceneCoordinate.x, bowSceneYCoordinate)
            }

            if (result.shipId != Cell.NoShip) {
                shipId = result.shipId

                if (orientation == Orientation.Vertical)
                    updateTranslation(result.normalizedLayoutX, result.normalizedLayoutY + 0.5 * height)
                else
                    updateTranslation(result.normalizedLayoutX + 0.5 * height, result.normalizedLayoutY)

                return
            }
        }

        returnToHarbour()
    }

    fun returnToHarbour() {
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