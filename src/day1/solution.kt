package day1

import readInput
import readSample

fun List<String>.parse() = this.map { it.toInt() }

fun ex1(input: List<Int>) = input
    .zipWithNext()
    .count { (current, next) -> next > current }

fun ex2(input: List<Int>) = input
    .windowed(size = 3)
    .map { window -> window.sum() }
    .zipWithNext()
    .count { (current, next) -> next > current }

fun main() {
    val sample = readSample(day = 1).parse()
    val input = readInput(day = 1).parse()

    assert(ex1(sample) == 7)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 5)
    println("Exercise 2: ${ex2(input)}")
}