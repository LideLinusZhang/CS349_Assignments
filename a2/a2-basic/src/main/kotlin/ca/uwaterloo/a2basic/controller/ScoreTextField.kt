package ca.uwaterloo.a2basic.controller

import javafx.geometry.Pos
import javafx.scene.control.TextField

class ScoreTextField : TextField() {
    init {
        alignment = Pos.CENTER
        prefWidth = 50.0
        prefHeight = 25.0

        this.focusedProperty().addListener { _, _, _ ->
            if (text != "WD") {
                val converted: Int? = text.toIntOrNull()
                if (converted == null || converted !in 0..100)
                    text = String()
            }
        }
    }
}