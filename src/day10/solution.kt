package day10

import readInput
import readSample
import java.util.*

fun checkCorrupted(s: String): Char? {
    val match = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    val stack = Stack<Char>()

    for (c in s) {
        if (c in match.keys) {
            stack.add(c)
        } else {
            val cc = stack.peek()
            if (c == match[cc]) {
                stack.pop()
            } else {
                return c
            }
        }
    }
    return null
}

fun scoreCorrupted(c: Char): Int = when (c) {
    ')' -> 3 as Int
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> throw Error("Unrecognised character: $c")
}

fun completeLine(s: String): String {
    val match = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    val stack = Stack<Char>()

    for (c in s) {
        if (c in match.keys) {
            stack.add(c)
        } else {
            stack.pop()
        }
    }
    return stack.map { match[it]!! }.reversed().joinToString("")
}

fun score(s: String): Long {
    var score = 0L
    for (c in s) {
        score *= 5
        score += when(c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw Error("Unrecognised character: $c")
        }
    }
    return score
}

fun ex1(input: List<String>): Int {
    return input.mapNotNull(::checkCorrupted).sumOf(::scoreCorrupted)
}

fun ex2(input: List<String>): Long {
    val filtered = input.filter {  checkCorrupted(it) == null }
    val completed = filtered.map(::completeLine)
    val scores = completed.map(::score).sorted()

    return scores[scores.size / 2]
}

fun main() {
    val sample = readSample(day = 10)
    val input = readInput(day = 10)

    assert(ex1(sample) == 26397)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 288957L)
    println("Exercise 2: ${ex2(input)}")
}