fun main() {

    /**
     * 16 (p), 38 (L), 42 (P), 22 (v), 20 (t), and 19 (s); the sum of these is 157.
     */
    fun Char.priority(): Int {
        return when (this) {
            in 'a'..'z' -> code - 96
            in 'A'..'Z' -> code - 38
            else -> 0
        }
    }

    fun splitIntoCompartments(rucksack: String): Pair<String, String> {
        val list = rucksack.chunked(rucksack.length / 2)
        return Pair(list.first(), list.last())
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { rucksack ->
            val (a, b) = splitIntoCompartments(rucksack)
            a.firstOrNull { item ->
                val match = b.firstOrNull { it == item }
                result += match?.priority() ?: 0
                match != null
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val listOfGroups = input.chunked(3)

        listOfGroups.forEach { group ->
            group[0].firstOrNull { item ->
                val match = group[1].firstOrNull { it == item }
                var allMatch: Char? = null
                if (match != null) {
                    allMatch = group[2].firstOrNull { match == it }
                    result += allMatch?.priority() ?: 0
                }
                allMatch != null
            }
        }
        return result
    }

    fun part2v2(input: List<String>): Int {
        val listOfGroups = input.chunked(3)

        return listOfGroups.map {
            val (a, b, c) = it
            val common = a.toSet() intersect b.toSet() intersect c.toSet()
            common.single()
        }.sumOf { it.priority() }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
//    val res = part1(testInput)
//    check(res == 157)
    val res = part2v2(testInput)
    check(res == 70)

//    val input = readInput("Day03")
//    println(part1(input))
//    println(part2(input))
}
