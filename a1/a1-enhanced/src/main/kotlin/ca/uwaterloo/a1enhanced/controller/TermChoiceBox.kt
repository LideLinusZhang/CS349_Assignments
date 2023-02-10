package ca.uwaterloo.a1enhanced.controller

import ca.uwaterloo.a1enhanced.model.Term
import javafx.scene.control.ChoiceBox

class TermChoiceBox : ChoiceBox<Term>() {
    init {
        items.addAll(Term.values())
        prefWidth = 60.0
        prefHeight = 25.0
    }
}