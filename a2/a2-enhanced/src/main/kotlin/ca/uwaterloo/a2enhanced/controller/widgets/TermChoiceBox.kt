package ca.uwaterloo.a2enhanced.controller.widgets

import ca.uwaterloo.a2enhanced.model.enums.Term
import javafx.scene.control.ChoiceBox

class TermChoiceBox : ChoiceBox<Term>() {
    init {
        items.addAll(Term.values())
        prefWidth = 60.0
        prefHeight = 25.0
    }
}