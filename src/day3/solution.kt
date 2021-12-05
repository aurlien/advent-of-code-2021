package day3

import bitsToInt
import readInput
import readSample

const val ONE = '1'
const val ZERO = '0'

data class BitCount(val zeros: Int, val ones: Int)

fun Map<Char, Int>.toBitCount(): BitCount {
    val zeros = this[ZERO] ?: 0
    val ones = this[ONE] ?: 0

    return BitCount(zeros, ones)
}

fun findRating(input: List<String>, bitCriteria: (List<String>, Int) -> Char): String {
    var candidates = input
    var index = 0
    while(candidates.size > 1) {
        candidates = candidates.filter { it[index] == bitCriteria(candidates, index)  }
        index += 1
    }
    return candidates.first()
}

fun mostCommon(input: List<String>, index: Int): Char {
    val (zeros, ones) = input.countBits(index)
    return if (zeros <= ones) ONE else ZERO
}

fun leastCommon(input: List<String>, index: Int): Char {
    val (zeros, ones) = input.countBits(index)
    return if (zeros > ones) ONE else ZERO
}

fun List<String>.countBits(index: Int): BitCount = this
    .groupingBy { row -> row[index] }
    .eachCount()
    .toBitCount()

fun ex1(input: List<String>): Number {
    val bits = input[0].length

    val counts = (0 until bits).map { index ->
        input
            .groupingBy { row -> row[index] }
            .eachCount()
            .toBitCount()
    }

    val gamma = counts.map { (zeros, ones) -> if (ones > zeros) ONE else ZERO }
    val epsilon = counts.map { (zeros, ones) -> if (zeros > ones) ONE else ZERO }

    return gamma.bitsToInt() * epsilon.bitsToInt()
}

fun ex2(input: List<String>): Number {
    val oxygenRating = findRating(input, ::mostCommon)
    val co2Rating = findRating(input, ::leastCommon)

    return oxygenRating.bitsToInt() * co2Rating.bitsToInt()
}

fun main() {
    val sample = readSample(day = 3)
    val input = readInput(day = 3)

    assert( ex1(sample) == 198)
    println("Exercise 1: ${ex1(input)}")

    assert( ex2(sample) == 230)
    println("Exercise 2: ${ex2(input)}")
}





