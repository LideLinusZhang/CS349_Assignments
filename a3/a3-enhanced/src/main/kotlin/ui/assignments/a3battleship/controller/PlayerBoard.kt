package ui.assignments.a3battleship.controller

import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.Orientation
import ui.assignments.a3battleship.model.Player
import ui.assignments.a3battleship.model.ShipType
import ui.assignments.a3battleship.view.Board

class PlayerBoard(game: Game) : Board(game = game, player = Player.Human) {
    data class PlaceResult(
        val shipId: Int,
        val normalizedLayoutX: Double,
        val normalizedLayoutY: Double
    )

    fun placeBattleship(
        shipType: ShipType,
        orientation: Orientation,
        bowLayoutXCoordinate: Double,
        bowLayoutYCoordinate: Double,
    ): PlaceResult {
        val boardXCoordinate = getBoardCoordinate(bowLayoutXCoordinate - sceneX)
        val boardYCoordinate = getBoardCoordinate(bowLayoutYCoordinate - sceneY)

        val shipId = game.placeShip(shipType, orientation, boardXCoordinate, boardYCoordinate)

        val normalizedLayoutX = if (orientation == Orientation.Vertical)
            getCanvasCoordinate(boardXCoordinate) + sceneX + 0.5 * gridSize
        else
            getCanvasCoordinate(boardXCoordinate) + sceneX
        val normalizedLayoutY = if (orientation == Orientation.Vertical)
            getCanvasCoordinate(boardYCoordinate) + sceneY
        else
            getCanvasCoordinate(boardYCoordinate) + sceneY + 0.5 * gridSize

        game.getBoard(player)

        return PlaceResult(shipId, normalizedLayoutX, normalizedLayoutY)
    }

    fun removeBattleship(shipId: Int) = game.removeShip(shipId)
}