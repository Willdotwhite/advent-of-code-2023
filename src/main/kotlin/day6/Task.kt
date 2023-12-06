package day6

import ITask

class Task: ITask {
    override fun solve(input: List<String>): Int {
        val times = """Time:\s+([\d| ]+)$""".toRegex().find(input[0])!!.groupValues[1]
            .split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toList()
        val distances =  """Distance:\s+([\d| ]+)$""".toRegex().find(input[1])!!.groupValues[1]
            .split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toList()

        return times.indices
            .map { Race(times[it], distances[it]) }
            .map { it.getWinningTimes().size }
            .reduce { acc, i -> acc * i }

    }
}

data class Race(val time: Int, val distance: Int) {
    fun getWinningTimes(): List<Int> {
        return (1..<time)
            .filter { (it)*(time-it) > distance }
            .toList()
    }
}