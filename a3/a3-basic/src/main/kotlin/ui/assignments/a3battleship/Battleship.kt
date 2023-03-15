package ui.assignments.a3battleship

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import ui.assignments.a3battleship.controller.ExitGameButton
import ui.assignments.a3battleship.controller.OpponentBoard
import ui.assignments.a3battleship.controller.PlayerBoard
import ui.assignments.a3battleship.controller.StartGameButton
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.view.BoardVBox
import ui.assignments.a3battleship.view.GameStatusText
import ui.assignments.a3battleship.view.Harbour

class Battleship : Application() {
    override fun start(stage: Stage) {
        val game = Game(10, true)
        //val player = UI(game)
        val computer = AI(game)

        val playerBoard = PlayerBoard(game)
        val opponentBoard = OpponentBoard(game)
        val harbour = Harbour(playerBoard, game)
        val gameStatusText = GameStatusText(game)
        game.registerViews(playerBoard, opponentBoard, harbour)

        val playerVBox = BoardVBox("My Formation", playerBoard)
        val opponentVBox = BoardVBox("Opponent’s Waters", opponentBoard)

        val centerVBox = VBox(gameStatusText, harbour, StartGameButton(harbour, game), ExitGameButton()).apply {
            prefWidth = 275.0
            prefHeight = 375.0
            alignment = Pos.TOP_CENTER
            VBox.setVgrow(children[0], Priority.NEVER)
            VBox.setVgrow(children[1], Priority.ALWAYS)
            VBox.setMargin(children[3], Insets(10.0, 0.0, 10.0, 0.0))
        }

        stage.apply {
            scene = Scene(HBox(playerVBox, centerVBox, opponentVBox), 875.0, 375.0)
            title = "CS349 - A3 Battleship - l625zhan"
            isResizable = false
        }.show()

        game.startGame()
    }
}