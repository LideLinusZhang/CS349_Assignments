package ui.assignments.a3battleship.model

import javafx.scene.image.Image

/**
 * ShipType enumerates all types of ships.
 */
enum class ShipType {
    Battleship,
    Carrier,
    Cruiser,
    Destroyer,
    Submarine;

    fun length(): Int {
        return when (this) {
            Destroyer -> 2
            Cruiser -> 3
            Submarine -> 3
            Battleship -> 4
            Carrier -> 5
        }
    }

    fun image(): Image {
        return when (this) {
            Destroyer -> Image("Destroyer.jpg")
            Cruiser -> Image("Cruiser.jpg")
            Submarine -> Image("Submarine.jpg")
            Battleship -> Image("Battleship.jpg")
            Carrier -> Image("Carrier.jpg")
        }
    }
}