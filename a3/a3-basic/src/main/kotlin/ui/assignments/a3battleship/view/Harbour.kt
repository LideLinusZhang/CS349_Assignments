package ui.assignments.a3battleship.view

import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import ui.assignments.a3battleship.controller.PlayerBoard
import ui.assignments.a3battleship.controller.Ship
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.GameState
import ui.assignments.a3battleship.model.Player
import ui.assignments.a3battleship.model.ShipType

class Harbour(playerBoard: PlayerBoard, private val game: Game) : HBox(), IView {
    val allShipsPlacedProperty: BooleanBinding
    private val fleet = mutableListOf<Ship>()

    init {
        prefWidth = 275.0
        prefHeight = 300.0
        alignment = Pos.TOP_CENTER

        ShipType.values().forEach {
            val ship = Ship(playerBoard, it)
            children.add(ship)
            fleet.add(ship)
        }

        allShipsPlacedProperty = fleet.fold(fleet.first().shipPlacedProperty) { acc, ship ->
            Bindings.and(acc, ship.shipPlacedProperty)
        }

        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(it, Insets(10.0))
        }
    }

    fun disableShipMoving() {
        fleet.forEach { it.disableMoving() }
    }

    override fun update() {
        if (game.gameStateProperty.value == GameState.HumanWon) {
            fleet.forEach {
                if (!game.isSunk(Player.Human, it.shipId))
                    it.returnToHarbour()
            }
        }
    }
}