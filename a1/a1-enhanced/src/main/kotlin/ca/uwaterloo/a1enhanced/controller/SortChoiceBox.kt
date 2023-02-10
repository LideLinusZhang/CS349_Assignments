package ca.uwaterloo.a1enhanced.controller

import ca.uwaterloo.a1enhanced.model.SortBy
import javafx.scene.control.ChoiceBox

class SortChoiceBox: ChoiceBox<SortBy>() {
    init {
        items.addAll(SortBy.values())
        prefWidth = 100.0
        prefHeight = 25.0
    }
}