inline fun <reified T> matrixOfNulls(height: Int, width: Int) =
    Array(height) { arrayOfNulls<T>(width) }

fun main() {

    fun Array<Array<Char?>>.print() {

        /** Test input
        println("[${this[3][0]}],[${this[3][1]}],[${this[3][2]}]")
        println("[${this[2][0]}],[${this[2][1]}],[${this[2][2]}]")
        println("[${this[1][0]}],[${this[1][1]}],[${this[1][2]}]")
        println("[${this[0][0]}],[${this[0][1]}],[${this[0][2]}]")
        */

        //  Real input
        println("[${this[7][0]}],[${this[7][1]}],[${this[7][2]}],[${this[7][3]}],[${this[7][4]}],[${this[7][5]}],[${this[7][6]}],[${this[7][7]}],[${this[7][8]}]")
        println("[${this[6][0]}],[${this[6][1]}],[${this[6][2]}],[${this[6][3]}],[${this[6][4]}],[${this[6][5]}],[${this[6][6]}],[${this[6][7]}],[${this[6][8]}]")
        println("[${this[5][0]}],[${this[5][1]}],[${this[5][2]}],[${this[5][3]}],[${this[5][4]}],[${this[5][5]}],[${this[5][6]}],[${this[5][7]}],[${this[5][8]}]")
        println("[${this[4][0]}],[${this[4][1]}],[${this[4][2]}],[${this[4][3]}],[${this[4][4]}],[${this[4][5]}],[${this[4][6]}],[${this[4][7]}],[${this[4][8]}]")
        println("[${this[3][0]}],[${this[3][1]}],[${this[3][2]}],[${this[3][3]}],[${this[3][4]}],[${this[3][5]}],[${this[3][6]}],[${this[3][7]}],[${this[3][8]}]")
        println("[${this[2][0]}],[${this[2][1]}],[${this[2][2]}],[${this[2][3]}],[${this[2][4]}],[${this[2][5]}],[${this[2][6]}],[${this[2][7]}],[${this[2][8]}]")
        println("[${this[1][0]}],[${this[1][1]}],[${this[1][2]}],[${this[1][3]}],[${this[1][4]}],[${this[1][5]}],[${this[1][6]}],[${this[1][7]}],[${this[1][8]}]")
        println("[${this[0][0]}],[${this[0][1]}],[${this[0][2]}],[${this[0][3]}],[${this[0][4]}],[${this[0][5]}],[${this[0][6]}],[${this[0][7]}],[${this[0][8]}]")

    }

    fun Array<Array<Char?>>.peakIndex(stackIndex: Int): Int {
        var peak = lastIndex
        while (peak > -1 && this[peak][stackIndex] == null) {
            peak -= 1
        }
        return peak
    }

    fun Array<Array<Char?>>.deleteCrateFrom(stack: Int): Char {
        val stackIndex = stack - 1
        val peak = peakIndex(stackIndex)
        val topCrate: Char = this[peak][stackIndex]!!
        this[peak][stackIndex] = null
        return topCrate
    }

    fun Array<Array<Char?>>.addCrateTo(crate: Char, stack: Int) {
        val stackIndex = stack - 1
        val peak = peakIndex(stackIndex) + 1
        this[peak][stackIndex] = crate
    }

    fun Array<Array<Char?>>.move(from: Int, to: Int) {
//        println("Moving crate from: $from to: $to")
        val removedCrate = deleteCrateFrom(from)
        addCrateTo(removedCrate, to)
    }

    fun Array<Array<Char?>>.moveAsStack(height: Int, from: Int, to: Int) {
        val removed = mutableListOf<Char>()
        repeat(height)  {
            removed.add(deleteCrateFrom(from))
        }

        removed.asReversed().forEach {
            addCrateTo(it, to)
        }
    }

    fun Array<Array<Char?>>.move(times: Int, from: Int, to: Int) {
        repeat(times) { move(from, to) }
    }

    fun Array<Array<Char?>>.topOfStacks(): String {
        val top = StringBuilder()
        for (i in 0..this[0].lastIndex) {
            val topChar = this[peakIndex(i)][i]
            top.append(topChar)
        }
        return top.toString()
    }

    fun initCargo(input: List<String>): Array<Array<Char?>> {
        val width = input.first { it.contains('1') }.last().toString().toInt()
        val height = width * 5  //8
        println("Creating matrix where width: $width and height: $height")
        val arrayMap = matrixOfNulls<Char>(height, width)

        val indexToColum = mutableMapOf<Int, Int>()
        input.first { it.contains('1') }.let { row ->
            row.filter { it.toString().isNotBlank() }.forEach { column ->
                indexToColum[row.indexOf(column)] = column.toString().toInt() - 1
            }
        }

        val stacks = input.takeWhile { !it.contains('1') }
        stacks.forEachIndexed { index, row ->
            indexToColum.keys.forEach { colIndex ->
                if (row.lastIndex >= colIndex && row[colIndex].isUpperCase()) {
                    val crate: Char = row[colIndex]
                    val rowNbr = stacks.lastIndex - index
                    val columnNbr = indexToColum[colIndex]!!
                    arrayMap[rowNbr][columnNbr] = crate
                }
            }
        }
        return arrayMap
    }

    fun part1(input: List<String>): String {
        val arrayMap = initCargo(input)

        input.filter { it.contains("move") }
            .map { it.substringAfter("move ") }
            .forEach {
                val (a, b, c) = it.split("from", "to")
                arrayMap.move(
                    a.trim().toInt(),
                    b.trim().toInt(),
                    c.trim().toInt()
                )
            }

        arrayMap.print()
        return arrayMap.topOfStacks()
    }

    fun part2(input: List<String>): String {
        val arrayMap = initCargo(input)

        input.filter { it.contains("move") }
            .map { it.substringAfter("move ") }
            .forEach {
                val (a, b, c) = it.split("from", "to")
                arrayMap.moveAsStack(
                    a.trim().toInt(),
                    b.trim().toInt(),
                    c.trim().toInt()
                )
            }

//        arrayMap.print()
        return arrayMap.topOfStacks()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
//    val res = part1(testInput)
//    check(res == "CMZ")
//    val res = part2(testInput)
//    check(res == "MCD")

    val input = readInput("Day05")
//    println(part1(input))
    println(part2(input))
}

