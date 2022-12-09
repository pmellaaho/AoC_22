fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        val value = input.fold(0) { r, t ->
            try {
                t.toInt() + r

            } catch (e: Exception) {
                if (r > max) max = r
                0
            }
        }
        return if (max > value) max else value
    }

    fun part2(input: List<String>): Int {
        val list = mutableListOf<Int>()
        val value = input.fold(0) { r, t ->
            try {
                t.toInt() + r

            } catch (e: Exception) {
                list.add(r)
                0
            }
        }

        list.add(value)
        list.sortDescending()
        return list.take(3)
            .reduce { s,t -> s+t }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    val res = part2(testInput)
//    check(res == 45000)

    val input = readInput("Day01")
//    println(part1(input))
    println(part2(input))
}
