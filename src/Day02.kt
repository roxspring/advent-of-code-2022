enum class Play(val score: Int) {
    Rock(1), Paper(2), Scissors(3);

    fun next(): Play = values()[(3 + ordinal + 1) % 3]
    fun prev(): Play = values()[(3 + ordinal - 1) % 3]
}

enum class Result(val score: Int) {
    Loss(0), Draw(3), Win(6);

    companion object {
        fun of(opponent: Play, strategy: Play): Result = when (strategy) {
            opponent.next() -> Win
            opponent.prev() -> Loss
            else -> Draw
        }
    }
}

data class Round(
    val opponent: Play,
    val strategy: Play,
    val result: Result
) {
    val score = strategy.score + result.score

    init {
        if (result != Result.of(opponent, strategy)) {
            throw Exception("Inconsistent round! $this should end in ${Result.of(opponent, strategy)}")
        }
    }

    override fun toString(): String {
        return "$opponent vs $strategy => $result ($score)"
    }
}


fun main() {

    fun part1(lines: List<String>): Int = lines
        .map { line ->
            val split = line.split(" ")
            val opponent = Play.values()["ABC".indexOf(split[0])]
            val strategy = Play.values()["XYZ".indexOf(split[1])]
            Round(
                opponent, strategy, Result.of(opponent, strategy)
            )
        }
        .sumOf { it.score }

    fun part2(lines: List<String>): Int {
        return lines
            .map { line ->
                val split = line.split(" ")
                val opponent = Play.values()["ABC".indexOf(split[0])]
                val result = Result.values()["XYZ".indexOf(split[1])]
                Round(
                    opponent,
                    strategy = when (result) {
                        Result.Draw -> opponent
                        Result.Win -> opponent.next()
                        Result.Loss -> opponent.prev()
                    },
                    result
                )
            }
            .sumOf { it.score }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}
