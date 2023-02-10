package ca.uwaterloo.a1enhanced.model

enum class SortBy {
    Code {
        override fun toString(): String = "Course Code"
    },
    Score,
    Term
}