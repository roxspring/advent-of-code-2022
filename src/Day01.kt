fun main() {

    fun part1(input: List<String>): Int = input.chunkedByBlank()
        .map { lines -> lines.sumOf { it.toInt() } }
        .max()

    fun part2(input: List<String>): Int = input.chunkedByBlank()
        .map { lines -> lines.sumOf { it.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}

/**
 * Splits the list into chunks delimited by blank lines.
 *
 * @return a sequence of lists of non-blank lines.
 */
fun List<String>.chunkedByBlank(): Sequence<List<String>> = sequence {
    var start = 0
    forEachIndexed { index, line ->
        if (line.isEmpty()) {
            yield(subList(start, index))
            start = index + 1
        }
    }
    yield(subList(start, size))
}

