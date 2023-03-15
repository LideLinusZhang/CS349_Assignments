package ui.assignments.a3battleship.view

import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import ui.assignments.a3battleship.model.Game
import ui.assignments.a3battleship.model.GameState

class GameStatusText(private val game: Game):Text("My Fleet"), IView {
    init {
        textAlignment = TextAlignment.CENTER
    }

    override fun update() {
        if (game.gameStateProperty.value == GameState.HumanWon)
            text = "You won!"
        else if (game.gameStateProperty.value == GameState.AiWon)
            text = "You were defeated!"
    }
}