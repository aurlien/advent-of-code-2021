package day11

import readInput
import readSample
import java.util.*
import kotlin.system.measureTimeMillis

typealias Board = Map<Point, Int>

data class Point(val x: Int, val y: Int)

fun List<String>.parse(): Board = this.flatMapIndexed { y, row ->
    row.mapIndexed { x, element ->
        Point(x, y) to element.digitToInt()
    }
}.toMap()

fun Board.rows() = keys.maxOf { it.y }

fun Board.columns() = keys.maxOf { it.x }

fun Board.print() {
    for (x in 0..columns()) {
        for (y in 0..rows()) {
            print("${this[Point(x, y)]}".padStart(3, ' '))
        }
        println()
    }
    println()
}

fun Board.neighbours(point: Point): List<Point> =
    listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1, 1 to 1, -1 to -1, -1 to 1, 1 to -1)
        .map { (dx, dy) -> Point(point.x + dx, point.y + dy) }
        .filter { (x, y) -> (y in 0..rows()) and (x in 0..columns()) }

fun Board.flash(point: Point): Board {
    val neighbours = this.neighbours(point)
    return this.mapValues { (k, v) ->
        when (k) {
            in neighbours -> v + 1
            else -> v
        }
    }
}

fun Board.step(): Pair<Board, Int> {
    var nextBoard = this.mapValues { it.value + 1 }
    val flashed = mutableSetOf<Point>()
    val queue = Stack<Point>()

    nextBoard.filter { it.value > 9 }.forEach { queue.add(it.key) }

    while (!queue.empty()) {
        val point = queue.pop()
        nextBoard = nextBoard.flash(point)
        flashed.add(point)

        nextBoard.filter { (it.value > 9) and (it.key !in flashed) and (it.key !in queue) }.forEach {
            queue.add(it.key)
        }
    }

    return Pair(nextBoard.mapValues { (key, value) -> if (key in flashed) 0 else value }, flashed.size)
}

fun ex1(input: Board): Int {
    data class Acc(val board: Board, val flashes: Int)
    return (0 until 100)
        .fold(Acc(board = input, flashes = 0)) { acc, _ ->
            acc.board.step().let { (board, flashes) -> Acc(board, acc.flashes + flashes) }
        }.flashes
}

fun ex2(input: Board): Int {
    tailrec fun loop(board: Board, step: Int): Int {
        board.step().let { (next, flashes) ->
            if (flashes == next.size) {
                return step
            }
            return loop(next, step + 1)
        }
    }

    return loop(board = input, step = 1)
}

fun main() {
    val sample = readSample(day = 11).parse()
    val input = readInput(day = 11).parse()

    assert(ex1(sample) == 1656)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 195)
    println("Exercise 2: ex2(input)}")
}