package ui.assignments.a3battleship

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
import ui.assignments.a3battleship.controller.OpponentBoard
import ui.assignments.a3battleship.controller.PlayerBoard
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.view.BoardVBox
import ui.assignments.a3battleship.view.Harbour

class Battleship : Application() {
    override fun start(stage: Stage) {
        val game = Game(10, true)
        //val player = UI(game)
        val computer = AI(game)

        val playerBoard = PlayerBoard(game)
        val playerVBox = BoardVBox("My Formation", playerBoard)
        val opponentVBox = BoardVBox("Opponent’s Waters", OpponentBoard(game))
        val harbour = Harbour(playerBoard)

        stage.apply {
            scene = Scene(HBox(playerVBox, harbour, opponentVBox), 875.0, 375.0)
            title = "CS349 - A3 Battleship - l625zhan"
            isResizable = false
        }.show()

        game.startGame()
    }
}