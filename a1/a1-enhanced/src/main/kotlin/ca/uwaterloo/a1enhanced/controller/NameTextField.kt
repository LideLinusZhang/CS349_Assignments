package ca.uwaterloo.a1enhanced.controller

import javafx.scene.control.TextField

class NameTextField : TextField() {
    init {
        maxWidth = Double.MAX_VALUE
        prefHeight = 25.0
    }
}