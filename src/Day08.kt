fun Char.height(): Int = toString().toInt()

fun String.tallestBefore(idx: Int): Int {
    return substring(0, idx).maxOf { it.height() }
}

fun String.tallestAfter(idx: Int): Int {
    return substring(idx + 1).maxOf { it.height() }
}

fun main() {

    val treeColums: MutableMap<Int, String> = mutableMapOf()

    fun part1(input: List<String>): Int {
        val visibleHorizontal = buildList {
            input.forEachIndexed { i, line ->
                line.forEachIndexed { x, tree ->

                    treeColums[x] = buildString {
                        treeColums[x]?.let { append(it) }
                        append(tree)
                    }

                    when (i) {
                        0 -> add(Pair(x, (input.lastIndex)))
                        input.lastIndex -> add(Pair(x, 0))

                        else -> {
                            when (x) {
                                // 1st and last of each row
                                0, line.lastIndex -> add(Pair(x, (input.lastIndex - i)))

                                // check 2nd las last of each row
                                line.lastIndex - 1 -> {
                                    if (tree.height() > line.last().height()) {
                                        add(Pair(x, (input.lastIndex - i)))
                                    }
                                }

                                // trees that are taller than trees before or after it in each row
                                else -> {
                                    if (tree.height() > line.tallestBefore(x)) {
                                        add(Pair(x, (input.lastIndex - i)))

                                    } else if (tree.height() > line.tallestAfter(x)) {
                                        add(Pair(x, (input.lastIndex - i)))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        val visibleVertical = buildList {
            treeColums.values.forEachIndexed { j, column ->
                column.forEachIndexed { y, tree ->

                    when (j) {
                        0, column.lastIndex -> { /* skip 1st and last column */
                        }

                        // trees that are taller than trees before or after it in each column
                        else -> {
                            if (y == 0 || y == column.lastIndex) { /* skip 1st and last for middle columns */
                            } else if (tree.height() > column.tallestBefore(y)) {
                                add(Pair(j, (column.lastIndex - y)))
                            } else if (tree.height() > column.tallestAfter(y)) {
                                add(Pair(j, (column.lastIndex - y)))
                            }
                        }
                    }
                }
            }
        }

        val visibleTrees = (visibleHorizontal + visibleVertical).toSet()
        println("Visible trees count: ${visibleTrees.size} and coordinates: $visibleTrees")
        return visibleTrees.size
    }


    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day08_test")
//    val res = part1(testInput)
//    check(res == 21)

    val input = readInput("Day08")
    println(part1(input))  // You guessed 1353, too low
//    println(part2(input))
}


