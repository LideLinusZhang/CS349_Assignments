package ca.uwaterloo.a2enhanced.model.enums

enum class CourseType {
    CS,
    Math,
    Other,
    All;

    fun creditRequired(): Int {
        return when (this) {
            CS -> 11
            Math -> 4
            Other -> 5
            All -> 20
        }
    }
}