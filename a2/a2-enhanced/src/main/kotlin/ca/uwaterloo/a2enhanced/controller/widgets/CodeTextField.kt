package ca.uwaterloo.a2enhanced.controller.widgets

import javafx.geometry.Pos
import javafx.scene.control.TextField

class CodeTextField : TextField() {
    init {
        alignment = Pos.CENTER
        prefWidth = 75.0
        prefHeight = 25.0
    }
}