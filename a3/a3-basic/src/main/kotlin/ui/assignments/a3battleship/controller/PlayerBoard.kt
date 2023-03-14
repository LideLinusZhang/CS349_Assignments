package ui.assignments.a3battleship.controller

import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.Orientation
import ui.assignments.a3battleship.model.Player
import ui.assignments.a3battleship.model.ShipType
import ui.assignments.a3battleship.view.Board

class PlayerBoard(game: Game) : Board(game = game, player = Player.Human) {
    data class PlaceResult (
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

        val normalizedLayoutX = getCanvasCoordinate(boardXCoordinate) + gridSize * 0.5 + sceneX
        val normalizedLayoutY = getCanvasCoordinate(boardYCoordinate) + gridSize * 0.5 + sceneY

        return PlaceResult(shipId, normalizedLayoutX, normalizedLayoutY)
    }
}