package ui.assignments.a3battleship.view

import javafx.scene.paint.Color
import ui.assignments.a3battleship.model.CellState

internal fun CellState.toColor(): Color {
    return when(this) {
        CellState.Ocean -> Color.DEEPSKYBLUE
        CellState.Attacked -> Color.LIGHTGRAY
        CellState.ShipHit -> Color.CORAL
        CellState.ShipSunk -> Color.DARKGRAY
    }
}