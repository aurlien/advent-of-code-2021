package day9

import readInput
import readSample

val DIGITS_REGEX = """\d""".toRegex()

private typealias Board = List<List<Int>>

private typealias Point = Pair<Int, Int>

private fun Board.get(x: Int, y: Int) = this[y][x]

private fun Board.neighbours(x: Int, y: Int): Map<Point, Int> {
    if (y >= this.size || x >= this.first().size) return emptyMap()

    return listOf(
        -1 to 0,
        0 to 1,
        1 to 0,
        0 to -1
    ).mapNotNull { (dx, dy) ->
        val xx = x + dx
        val yy = y + dy

        this.getOrNull(y + dy)?.getOrNull(x + dx)?.let {
            Pair(xx, yy) to it
        }
    }.toMap()
}

fun Board.basin(x: Int, y: Int): Int {
    val low = this.get(x, y)
    val res = mutableSetOf(x to y)
    var queue = this.neighbours(x, y).filter { it.value  in (low+1) until  9  }.map { it.key }.toSet()
    while (queue.isNotEmpty()) {
        val nextQueue = mutableSetOf<Point>()
        for (point in queue) {
            val (px, py) = point
            val adj = neighbours(px, py)
            nextQueue += adj.filter { it.value in (get(px, py) +1 ) until 9 }.map { it.key }
            res += point
        }
        queue = nextQueue
    }

    return res.size
}

private fun ex1(input: List<List<Int>>): Int {
    val lowPoints = mutableMapOf<Point, Int>()
    input.indices.map { y ->
        input[y].indices.map { x ->
            val value = input[y][x]
            if (value < input.neighbours(x, y).values.minOrNull()!!) {
                lowPoints += Pair(x, y) to value
            }
        }
    }
    return lowPoints.map { it.value + 1 }.sum()
}

private fun ex2(input: List<List<Int>>): Int {
    val lowPoints = mutableMapOf<Point, Int>()
    input.indices.map { y ->
        input[y].indices.map { x ->
            val value = input[y][x]
            if (value < input.neighbours(x, y).values.minOrNull()!!) {
                lowPoints += Pair(x, y) to value
            }
        }
    }

    val basins = lowPoints.map { (point, _) ->
        input.basin(point.first, point.second)
    }

    val top3 = basins.sorted().takeLast(3)

    return top3.reduce { acc, i -> acc * i  }
}

fun main() {
    val sample = readSample(day = 9).map { line -> DIGITS_REGEX.findAll(line).map { it.value.toInt() }.toList() }
    val input = readInput(day = 9).map { line -> DIGITS_REGEX.findAll(line).map { it.value.toInt() }.toList() }

    assert(ex1(sample) == 15)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 1134)
    println("Exercise 2: ${ex2(input)}")
}



