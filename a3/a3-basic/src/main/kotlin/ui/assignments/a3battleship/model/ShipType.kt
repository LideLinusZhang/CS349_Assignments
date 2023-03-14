package ui.assignments.a3battleship.model

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

    fun abbreviation(): String {
        return when (this) {
            Destroyer -> "DD"
            Cruiser -> "CC"
            Submarine -> "SB"
            Battleship -> "BB"
            Carrier -> "CV"
        }
    }
}