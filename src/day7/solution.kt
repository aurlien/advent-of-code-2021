package day7

import readInput
import readSample
import kotlin.math.abs

fun List<String>.parse() = this.first().strip().split(",").map(String::toInt)

fun solve(input: List<Int>, distanceFunction: (Int, Int) -> Int): Int {
    val min = input.minOrNull()!!
    val max = input.maxOrNull()!!

    val fuelCosts = (min..max).map { a ->
        input.sumOf{ b -> distanceFunction(a, b)}
    }

    return fuelCosts.minOrNull()!!
}

fun absoluteDistance(a: Int, b: Int) = abs(b - a)

fun triangularDistance(a: Int, b: Int) = abs(b - a).let { n -> (n * (n + 1)) / 2 }

fun main() {
    val sample = readSample(day = 7).parse()
    val input = readInput(day = 7).parse()

    val sampleEx1 = solve(sample, ::absoluteDistance)
    assert(sampleEx1 == 37)
    println("Exercise 1: ${solve(input, ::absoluteDistance)}")

    val sampleEx2 = solve(sample, ::triangularDistance)
    assert(sampleEx2 == 168)
    println("Exercise 2: ${solve(input, ::triangularDistance)}")

}

