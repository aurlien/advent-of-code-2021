package day8

import readInput
import readSample

fun translate(input: List<String>, output: List<String>): Int {
    val patternsByLength = (input + output).map { it.toSet() }.toSet().groupBy { it.size }

    val one = patternsByLength[2]!!.first()
    val four = patternsByLength[4]!!.first()
    val seven = patternsByLength[3]!!.first()
    val eight = patternsByLength[7]!!.first()

    val six = patternsByLength[6]!!.first { !one.all { c -> c in it } }
    val nine = patternsByLength[6]!!.first{ it != six && four.all { c -> c in it } }
    val zero = patternsByLength[6]!!.first{ it != six && it != nine }

    val three = patternsByLength[5]!!.first { one.all { c -> c in it } }
    val five = patternsByLength[5]!!.first { it.all { c -> c in six } }
    val two = patternsByLength[5]!!.first{ it != five && it != three}

    val map = mapOf(
        zero to 0,
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9
    )

    return output.map { map[it.toSet()]!! }.joinToString("").toInt()
}

private fun ex1(sample: List<Pair<List<String>, List<String>>>): Int {
    val res = sample.flatMap { (_, output) ->
        output.mapNotNull { pattern ->
            when (pattern.length) {
                2 -> "1"
                3 -> "7"
                4 -> "4"
                7 -> "8"
                else -> null
            }
        }
    }.count()
    return res
}

fun ex2(input: List<Pair<List<String>, List<String>>>) = input.sumOf { (i, o) -> translate(i, o) }

fun main() {
    val sample = readSample(day = 8).map { it.split(" | ") }.map { (input, output) -> input.split(" ") to output.split(" ") }
    val input = readInput(day = 8).map { it.split(" | ") }.map { (input, output) -> input.split(" ") to output.split(" ") }

    assert(ex1(sample) == 26)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 61229)
    println("Exercise 2: ${ex2(input)}")
}
