fun main() {
    class State {
        val instructionRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val stacks: MutableList<MutableList<Char>> = mutableListOf()

        fun push(stackIndex: Int, content: Char): State {
            if (content == ' ') {
                return this
            }

            for (i in stacks.size..stackIndex) {
                stacks.add(mutableListOf())
            }

            stacks[stackIndex] += content

            return this
        }

        fun moveOneByOne(instruction: String): State =
            instructionRegex.matchEntire(instruction)!!.destructured.let { (count, from, to) ->
                moveOneByOne(count.toInt(), from.toInt() - 1, to.toInt() - 1)
            }

        fun moveOneByOne(count: Int, fromIndex: Int, toIndex: Int): State {
            for (n in 0 until count) {
                val content = stacks[fromIndex].removeLast()
                stacks[toIndex].add(content)
            }
            return this
        }

        fun moveByBlock(instruction: String): State =
            instructionRegex.matchEntire(instruction)!!.destructured.let { (count, from, to) ->
                moveByBlock(count.toInt(), from.toInt() - 1, to.toInt() - 1)
            }

        fun moveByBlock(count: Int, fromIndex: Int, toIndex: Int): State {
            val fromStack = stacks[fromIndex]
            val toStack = stacks[toIndex]
            toStack.addAll(fromStack.subList(fromStack.size - count, fromStack.size))
            for (i in 0 until count) {
                fromStack.removeLast()
            }
            return this
        }

        fun tops(): String = stacks.map { it.last() }.joinToString("")

        override fun toString(): String = stacks.maxOfOrNull { it.size }!!.downTo(0).joinToString("\n") { depth ->
            stacks.joinToString(" ") { stack ->
                stack.getOrNull(depth)?.let { "[$it]" } ?: "   "
            }
        } + "\n" + (0 until stacks.size).joinToString(" ") { " ${it + 1} " }
    }

    fun List<String>.toState(): State = reversed().drop(1).fold(State()) { state, line ->
        (1..line.length step 4).fold(state) { state2, i ->
            state2.push(i / 4, line[i])
        }
    }

    fun part1(lines: List<String>): String = lines.chunkedByBlank().toList().let {
        val initialState = it[0].toState()
        val instructions = it[1]
        instructions.fold(initialState) { state, instruction ->
            state.moveOneByOne(instruction)
        }
    }.tops()

    fun part2(lines: List<String>): String = lines.chunkedByBlank().toList().let {
        val initialState = it[0].toState()
        val instructions = it[1]
        instructions.fold(initialState) { state, instruction ->
            state.moveByBlock(instruction)
        }
    }.tops()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}
