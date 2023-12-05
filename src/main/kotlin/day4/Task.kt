package day4

import ITask
import kotlin.math.pow

class Task: ITask {
    override fun solve(input: List<String>): Int {
        return input
            .map {
                val (winningNumbers, scoringNumbers) = """^Card\s+\d+: ([\d| ]+) \| ([\d| ]+)$""".toRegex()
                    .find(it)!!.destructured
                Pair(winningNumbers, scoringNumbers)
            }
            .map { (winningNumbers, scoringNumbers) ->
                scoringNumbers
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .filter { winningNumbers.split(" ").filter { it.isNotEmpty() }.contains(it) }
                    .size
            }
            .sumOf { if (it > 0) 2.0.pow(it - 1).toInt() else 0 }
    }
}