package day12

import readInput
import readSample

const val START = "start"
const val END = "end"

typealias Node = String

data class Graph(val nodes: Set<Node>, val edges: Map<Node, List<Node>>)

private fun List<String>.parse() = this.map { it.split("-") }.let {
    val nodes = it.flatMap { pair -> pair.toList() }.filter { node -> node != START && node != END }.toSet()

    val edges = it
        .flatMap { (n1, n2) -> listOf(n1 to n2, n2 to n1) }
        .filter { (n1, n2) -> n2 != START && n1 != END }
        .groupBy { (n1, _) -> n1 }
        .mapValues { (_, ns) -> ns.map { (_, n2) -> n2 } }

    Graph(nodes, edges)
}

fun Graph.paths(from: Node = START, visited: Set<String> = emptySet()): Int {
    if (from == END) return 1
    val out = edges[from]?.filter { it !in visited } ?: emptyList()
    val isSmallCave = from.lowercase() == from && from != START
    val nextVisited = if (isSmallCave) visited + from else visited

    return out.sumOf { n -> paths(n, nextVisited) }
}

fun Graph.pathsWithCycle(from: Node = START, visited: Set<String> = emptySet(), hasCycle: Boolean = false): List<String> {
    if (from == END) return listOf(END)
    val out = edges[from]?.filter { it !in visited } ?: emptyList()
    val isSmallCave = from.lowercase() == from && from != START

    val nextVisited = if (isSmallCave) visited + from else visited
    val paths = out.flatMap { n -> pathsWithCycle(n, nextVisited, hasCycle).map { path -> "$from,$path" } }
    return if (!hasCycle && isSmallCave) {
        (paths + out.flatMap { n -> pathsWithCycle(n, visited, hasCycle = true).map { path -> "$from,$path" } })
            .toSet()
            .toList()
    } else paths
}

fun List<String>.println() = forEach { line -> println(line) }

fun main() {
    val sample = readSample(day = 12).parse()
    val input = readInput(day = 12).parse()

    assert(sample.paths() == 226)
    println("Exercise 1: ${input.paths()}")

    assert(sample.pathsWithCycle().size == 3509)
    println("Exercise 2: ${input.pathsWithCycle().size}")
}