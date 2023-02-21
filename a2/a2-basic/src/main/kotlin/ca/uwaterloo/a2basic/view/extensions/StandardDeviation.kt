package ca.uwaterloo.a2basic.view.extensions

import kotlin.math.pow
import kotlin.math.sqrt

fun List<Int>.standardDeviation(): Double {
    val mean = average()
    return sqrt(fold(0.0) { accumulator, next -> accumulator + (next - mean).pow(2.0) } / size)
}