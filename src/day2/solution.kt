package day2

import org.jetbrains.annotations.TestOnly
import readInput
import readSample

fun List<String>.parse() = this.map {
    it.split(" ").let { (command, steps) -> command to steps.toInt() }
}

fun ex1(input: List<Pair<String, Int>>): Int {
    var x = 0
    var y = 0

    for ((command, steps) in input) {
        when (command) {
            "down" -> y += steps
            "up" -> y -= steps
            "forward" -> x += steps
        }
    }

    return y * x
}

fun ex2(input: List<Pair<String, Int>>): Int {
    var x = 0
    var y = 0
    var aim = 0

    for ((command, steps) in input) {
        when (command) {
            "down" -> aim += steps
            "up" -> aim -= steps
            "forward" -> {
                x += steps
                y += aim * steps
            }
        }
    }

    return y * x
}

fun main() {
    val sample = readSample(day = 2).parse()
    val input = readInput(day = 2).parse()

    assert(ex1(sample) == 150)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 900)
    println("Exercise 2: ${ex2(input)}")
}