package day4

import java.io.File

typealias Board = List<Int>
data class Input(val draws: List<Int>, val boards: List<Board>)

fun readInput(sample: Boolean = false): Input {
    val fileName = if (sample) "sample" else "input"
    val path = "src/day4/$fileName.txt"
    val input = File(path).readText().split("\n".repeat(2))

    val draws = input.first().split(",").map { it.toInt() }
    val boards = input.drop(1).map { board -> board.strip().split("""\s+""".toRegex()).map { el -> el.toInt() } }

    return Input(draws, boards)
}

fun isBingo(matches: List<Int>): Boolean {
    val rows = matches.map { match -> match.div(5) }
    val rowCounts = rows.groupBy { it }.map { (_, matches) -> matches.size }
    val rowBingo = rowCounts.maxByOrNull { it } == 5

    val cols = matches.map { match -> match % 5 }
    val colCounts = cols.groupBy { it }.map { (_, matches) -> matches.size }
    val colBingo = colCounts.maxByOrNull { it } == 5

    return rowBingo or colBingo
}

fun ex1(draws: List<Int>, boards: List<Board>): Int {
    val matches = boards.map { mutableListOf<Int>() }
    draws.forEach { draw ->
        boards.indices.forEach { b ->
            boards[b].indexOf(draw).let { if (it != -1) matches[b].add(it) }
            if (isBingo(matches[b])) {
                val unmarkedNumbers = boards[b].filterIndexed { i, _ -> i !in matches[b] }
                return unmarkedNumbers.sum() * draw
            }
        }
    }
    throw Error("No bingo found")
}

fun ex2(draws: List<Int>, boards: List<Board>): Int {
    val matches = boards.map { mutableListOf<Int>() }

    val bingos = mutableListOf<Int>()
    val bingoDraws = mutableListOf<Int>()
    draws.forEach { draw ->
        boards.indices
            .filter { it !in bingos }
            .forEach { b ->
                boards[b].indexOf(draw).let { if (it != -1) matches[b].add(it) }
                if (isBingo(matches[b])) {
                    bingos.add(b)
                    bingoDraws.add(draw)
                }
            }
    }
    val unmarkedNumbers = boards[bingos.last()].filterIndexed { i, _ -> i !in matches[bingos.last()] }
    return unmarkedNumbers.sum() * bingoDraws.last()
}

fun main() {
    val sample = readInput(sample = true)
    val input = readInput(sample = false)

    assert(ex1(sample.draws, sample.boards) == 4512)
    println("Exercise 1: ${ex1(input.draws, input.boards)}")

    assert(ex1(sample.draws, sample.boards) == 1924)
    println("Exercise 2: ${ex2(input.draws, input.boards)}")
}

