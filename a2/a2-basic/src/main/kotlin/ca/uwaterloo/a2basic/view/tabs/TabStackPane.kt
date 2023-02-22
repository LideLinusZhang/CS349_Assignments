package ca.uwaterloo.a2basic.view.tabs

import ca.uwaterloo.a2basic.model.CourseList
import ca.uwaterloo.a2basic.view.IView
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.layout.StackPane

class TabStackPane<T>(content: T) : StackPane(content) where T : Node, T : IView {
    init {
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE
        minWidth = 1.0
        minHeight = 1.0
        alignment = Pos.CENTER

        CourseList.registerView(content)
    }
}