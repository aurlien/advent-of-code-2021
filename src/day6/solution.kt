package day6

import readInput
import readSample

fun List<String>.parse() = this.first().split(",").map { it.toInt() }

fun ex1(input: List<Int>, days: Int = 80): Int {
    var fish: List<Int> = input
    for (d in 0 until days) {
        fish = fish.map { days ->
            if (days == 0) 6
            else days - 1
        } + (0 until fish.count { it == 0 }).map { 8 }
    }
    return fish.count()
}

fun ex2(input: List<Int>, days: Int = 256): Long {
    var counts = input.groupBy { it }.mapValues { it.value.size.toLong() }
    for (day in 0 until days) {
        val nextCounts = (0..8).associateWith { 0L }.toMutableMap()

        for (key in counts.keys.filter { it != 0 }) {
            nextCounts[key - 1] = counts[key]!!
        }
        nextCounts[6] = nextCounts[6]!! + (counts[0] ?: 0)
        nextCounts[8] = counts[0] ?: 0

        counts = nextCounts
    }
    return counts.values.sum()
}

fun main() {
    val sample = readSample(day = 6).parse()
    val input = readInput(day = 6).parse()

    assert(ex1(sample, days = 80) == 5934)
    println("Exercise 1: ${ex1(input, days = 80)}")

    assert(ex2(sample, days = 256) == 26984457539)
    println("Exercise 2: ${ex2(input, days=256)}")
}

