enum class Weapon(private val char: Char) {
    Rock('A'),  // 'X'
    Paper('B'), // 'Y'
    Scissors('C');  // 'Z'

    companion object {
        fun getByChar(char: Char): Weapon {
            values().forEach {
                if (it.char == char) return it
            }
            return Rock
        }

        fun getByResult(other: Weapon, result: Result): Weapon {
            return when (other) {
                Rock -> when (result) {
                    Result.Lose -> Scissors
                    Result.Draw -> Rock
                    Result.Win -> Paper
                }
                Paper -> when (result) {
                    Result.Lose -> Rock
                    Result.Draw -> Paper
                    Result.Win -> Scissors
                }
                Scissors -> when (result) {
                    Result.Lose -> Paper
                    Result.Draw -> Scissors
                    Result.Win -> Rock
                }
            }
        }
    }
}

enum class Result(private val char: Char) {
    Lose('X'),
    Draw('Y'),
    Win('Z');

    companion object {
        fun getByChar(char: Char): Result {
            values().forEach {
                if (it.char == char) return it
            }
            return Lose
        }
    }
}


data class Round(val opponent: Weapon, val you: Weapon) {
    fun score() = when (opponent) {
        Weapon.Rock -> when (you) {
            Weapon.Rock -> 1 + 3  // 1 for Rock and 3 for tie
            Weapon.Paper -> 2 + 6 // 2 for Paper and 6 for win
            Weapon.Scissors -> 3  // 3 for Scissors and 0 losing
        }

        Weapon.Paper -> when (you) {
            Weapon.Rock -> 1
            Weapon.Paper -> 2 + 3
            Weapon.Scissors -> 3 + 6
        }

        Weapon.Scissors -> when (you) {
            Weapon.Rock -> 1 + 6
            Weapon.Paper -> 2
            Weapon.Scissors -> 3 + 3
        }
    }

    companion object {
        fun newInstance(input: String) : Round {
            val opponent = Weapon.getByChar(input[0])
            val you = Weapon.getByResult(opponent, Result.getByChar(input[2]))
            return Round(opponent, you)
        }
    }
}

fun main() {

    fun part1(input: List<String>) =
        input.fold(0) { total, t ->
            total + Round.newInstance(t).score()
        }


    /**
     * the second column says how the round needs to end: X means you need to lose,
     * Y means you need to end the round in a draw,
     * and Z means you need to win.
     */
    fun part2(input: List<String>) =
        input.fold(0) { total, t ->
            total + Round.newInstance(t).score()
        }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    val res = part2(testInput)
//    check(res == 12)

    val input = readInput("Day02")
//    println(part1(input))
    println(part2(input))
}
