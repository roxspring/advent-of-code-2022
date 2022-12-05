fun main() {

    fun Char.priority(): Int = when (this) {
        in 'a'..'z' -> code - 'a'.code + 1
        in 'A'..'Z' -> code - 'A'.code + 26 + 1
        else -> throw Exception("WTF? $this")
    }

    fun String.uniqueCharactersInCommonWith(other: String): String =
        toCharArray().filter { other.contains(it) }.distinct().joinToString()

    fun part1(lines: List<String>): Int = lines.asSequence()
        .map { line -> Pair(line.substring(0 until line.length / 2), line.substring(line.length / 2)) }
        .map { (left, right) ->
            left.uniqueCharactersInCommonWith(right).first()
        }
        .sumOf { it.priority() }

    fun part2(lines: List<String>): Int = lines.asSequence()
        .chunked(3)
        .map { group -> group[0].uniqueCharactersInCommonWith(group[1]).uniqueCharactersInCommonWith(group[2]).first() }
        .sumOf { it.priority() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}
