package ui.assignments.a3battleship

import ui.assignments.a3battleship.model.Game
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import ui.assignments.a3battleship.controller.OpponentBoard

class Battleship : Application() {
    override fun start(stage: Stage) {
        val game = Game(10, true)
        //val player = UI(game)
        val computer = AI(game)

        stage.apply {
            scene = Scene(Group(OpponentBoard(game)), 875.0, 375.0)
            title = "CS349 - A3 Battleship - l625zhan"
            isResizable = false
        }.show()
    }
}