fun main() {

    fun String.parseRange(): IntRange =
        substring(0 until indexOf('-')).toInt()..substring(indexOf('-') + 1).toInt()

    fun String.parsePair(): Pair<IntRange, IntRange> =
        substring(0 until indexOf(',')).parseRange() to substring(indexOf(',') + 1).parseRange()

    fun IntRange.containsAll(other: IntRange): Boolean = all { other.contains(it) }

    fun IntRange.containsAny(other: IntRange): Boolean = any { other.contains(it) }

    fun part1(lines: List<String>): Int = lines.asSequence()
        .map { it.parsePair() }
        .map { (first, second) -> first.containsAll(second) || second.containsAll(first) }
        .count { it }

    fun part2(lines: List<String>): Int = lines.asSequence()
        .map { it.parsePair() }
        .map { (first, second) -> first.containsAny(second) || second.containsAny(first) }
        .count { it }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}
