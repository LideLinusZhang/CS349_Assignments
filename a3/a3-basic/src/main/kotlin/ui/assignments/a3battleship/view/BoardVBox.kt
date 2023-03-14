package ui.assignments.a3battleship.view

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Text

class BoardVBox(text: String, board: Board): VBox(Text(text), board) {
    init {
        prefWidth = 300.0
        maxHeight = Double.MAX_VALUE
        alignment = Pos.CENTER
        setVgrow(children[0], Priority.ALWAYS)
        setVgrow(children[1], Priority.NEVER)
        viewOrder = Double.MAX_VALUE
    }
}