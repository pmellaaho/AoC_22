fun main() {

    fun String.allDifferent(): Boolean {
        forEachIndexed { index, c ->
            for (i in index + 1..lastIndex) {
                if (this[i] == c) return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val windowSize = 4
        val count = input.first().windowed(windowSize, 1)
            .takeWhile {
                !it.allDifferent()
            }
        return count.size + windowSize
    }

    fun part2(input: List<String>): Int {
        val windowSize = 14
        val count = input.first().windowed(windowSize, 1)
            .takeWhile {
                !it.allDifferent()
            }
        return count.size + windowSize
    }


    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day06_test")
//    val res = part1(testInput)
//    check(res == 11)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
