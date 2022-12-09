fun main() {

    fun stringToRanges(row: String): Pair<IntRange, IntRange> {
        return row.split(",").map {
            val nbrs = it.split("-")
            nbrs.first().toInt()..nbrs.last().toInt()
        }.let {
            Pair(it.first(), it.last())
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { row ->
            val (r1, r2) = stringToRanges(row)
            val common = r1.toSet() intersect r2.toSet()

            if (common.size == r1.toSet().size || common.size == r2.toSet().size) 1
            else 0
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { row ->
            val (r1, r2) = stringToRanges(row)
            val common = r1.toSet() intersect r2.toSet()

            if (common.isNotEmpty()) 1
            else 0
        }.sumOf { it }
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
//    val res = part1(testInput)
//    check(res == 2)
//    val res = part2(testInput)
//    check(res == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
