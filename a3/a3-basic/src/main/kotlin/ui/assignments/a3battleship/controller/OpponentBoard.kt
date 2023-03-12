package ui.assignments.a3battleship.controller

import javafx.event.EventHandler
import ui.assignments.a3battleship.model.CellState
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.GameState
import ui.assignments.a3battleship.model.Player
import ui.assignments.a3battleship.view.Board

class OpponentBoard(game: Game) : Board(game = game, player = Player.Ai) {
    init {
        onMouseClicked = EventHandler {
            if (game.gameStateProperty.value == GameState.HumanAttack) {
                val boardXCoordinate = getBoardCoordinate(it.x)
                val boardYCoordinate = getBoardCoordinate(it.y)

                if (game.getBoard(player)[boardYCoordinate][boardXCoordinate] == CellState.Ocean) {
                    game.attackCell(boardXCoordinate, boardYCoordinate)

                    draw()
                }
            }
        }
    }
}