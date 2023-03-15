package ui.assignments.a3battleship.controller

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.Button

class ExitGameButton: Button("Exit Game") {
    init {
        prefWidth = 275.0
        onAction = EventHandler { Platform.exit() }
    }
}