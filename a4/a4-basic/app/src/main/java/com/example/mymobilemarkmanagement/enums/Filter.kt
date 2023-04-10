package com.example.mymobilemarkmanagement.enums

enum class Filter(private val displayName: String) {
    AllCourses("All Courses"),
    CSOnly("CS Only"),
    MathOnly("Math Only"),
    OtherOnly("Other Only");

    override fun toString(): String {
        return displayName
    }
}