package com.example.mymobilemarkmanagement.enums

enum class SortOrder(private val displayName: String) {
    ByCourseName("By Course Name"),
    ByTerm("By Term"),
    ByMark("By Mark");

    override fun toString(): String {
        return displayName
    }
}