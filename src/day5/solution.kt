package day5

import readInput
import readSample

data class Point(val x: Int, val y: Int)

val POINTS = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

fun List<String>.parse() = this.map { line ->
    POINTS.find(line)!!.let { match ->
        match
            .groupValues
            .drop(1)
            .map { num -> num.toInt() }
    }.let { (x0, y0, x1, y1) ->
        Pair(Point(x0, y0), Point(x1, y1))
    }
}

infix fun Int.towards(to: Int) = (this .. to).takeIf { !it.isEmpty() } ?: (this downTo to)

fun lines(p0: Point, p1: Point): List<Point> {
    val xs = p0.x towards p1.x
    val ys = p0.y towards p1.y

    return when {
        p0.x == p1.x -> ys.map { y -> Point(p0.x, y) }
        p0.y == p1.y -> xs.map { x -> Point(x, p0.y) }
        else -> xs.zip(ys).map { (x, y) -> Point(x, y) }
    }
}

fun ex1(input: List<Pair<Point, Point>>): Int {
    return input.filter { (p0, p1) ->
        p0.x == p1.x || p0.y == p1.y
    }.flatMap { (p0, p1) ->
        lines(p0, p1)
    }.groupBy {
        it
    }.filter { (key, value) ->
        value.size > 1
    }.count()
}

fun ex2(input: List<Pair<Point, Point>>): Int {
    val overlaps = input.flatMap { (p0, p1) ->
        lines(p0, p1)
    }.groupBy {
        it
    }

    return overlaps.filter { (_, value) ->
        value.size > 1
    }.count()
}


fun main() {
    val sample = readSample(day = 5).parse()
    val input = readInput(day = 5).parse()

    assert(ex1(sample) == 5)
    println("Exercise 1: ${ex1(input)}")

    assert(ex2(sample) == 12)
    println("Exercise 2: ${ex2(input)}")
}

