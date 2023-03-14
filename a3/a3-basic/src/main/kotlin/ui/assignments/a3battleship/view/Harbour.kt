package ui.assignments.a3battleship.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import ui.assignments.a3battleship.controller.PlayerBoard
import ui.assignments.a3battleship.controller.Ship
import ui.assignments.a3battleship.model.ShipType

class Harbour(playerBoard: PlayerBoard): HBox() {
    init {
        ShipType.values().forEach {
            children.add(Ship(playerBoard, it))
        }

        children.forEach {
            setHgrow(it, Priority.NEVER)
            setMargin(it, Insets(10.0))
        }

        prefWidth = 275.0
        alignment = Pos.CENTER
    }
}