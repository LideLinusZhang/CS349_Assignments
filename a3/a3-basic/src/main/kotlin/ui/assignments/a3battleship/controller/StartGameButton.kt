package ui.assignments.a3battleship.controller

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.GameState
import ui.assignments.a3battleship.view.Harbour

class StartGameButton(harbour: Harbour, game: Game) : Button("Start Game") {
    init {
        prefWidth = 275.0

        disableProperty().bind(
            Bindings.and(
                harbour.allShipsPlacedProperty,
                Bindings.equal(game.gameStateProperty, GameState.HumanSetup)
            ).not()
        )

        onAction = EventHandler {
            harbour.disableShipMoving()
            game.startGame()
        }
    }
}