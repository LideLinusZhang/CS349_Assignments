package com.example.mymobilemarkmanagement.models

import androidx.lifecycle.MutableLiveData
import com.example.mymobilemarkmanagement.enums.Term

data class Course(
    val description: MutableLiveData<String>,
    val mark: MutableLiveData<Int>,
    val term: MutableLiveData<Term>
) {
    constructor(description: String, mark: Int, term: Term) : this(
        MutableLiveData(description),
        MutableLiveData(mark),
        MutableLiveData(term)
    )

    companion object {
        const val WDMark: Int = -1
    }
}
