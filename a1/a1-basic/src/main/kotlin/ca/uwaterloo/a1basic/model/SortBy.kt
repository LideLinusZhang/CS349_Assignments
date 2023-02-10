package ca.uwaterloo.a1basic.model

enum class SortBy {
    Code {
        override fun toString(): String = "Course Code"
    },
    ScoreDescending {
        override fun toString(): String = "Score (Descending)"
    },
    Term,
    ScoreAscending {
        override fun toString(): String = "Score (Ascending)"
    }
}