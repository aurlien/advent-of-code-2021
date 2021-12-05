import java.io.File

fun readInput(day: Int) =  File("src/day$day/input.txt").readLines()

fun readSample(day: Int) =  File("src/day$day/sample.txt").readLines()

fun String.bitsToInt() = Integer.parseInt(this, 2)

fun List<Char>.bitsToInt() = joinToString("").bitsToInt()