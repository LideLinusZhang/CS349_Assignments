package ca.uwaterloo.a1basic.controller

import ca.uwaterloo.a1basic.model.Term
import javafx.scene.control.ChoiceBox

class TermChoiceBox : ChoiceBox<Term>() {
    init {
        items.addAll(Term.values())
        prefWidth = 60.0
        prefHeight = 25.0
    }
}