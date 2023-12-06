package day6

import ITask

class Task: ITask {
    override fun solve(input: List<String>): Int {
        val time = """Time:\s+([\d| ]+)$""".toRegex().find(input[0])!!.groupValues[1]
            .split("").filter { it.isNotBlank() }.fold("") {acc, s -> acc + s}.toLong()
        val distance =  """Distance:\s+([\d| ]+)$""".toRegex().find(input[1])!!.groupValues[1]
            .split(" ").filter { it.isNotBlank() }.fold("") {acc, s -> acc + s}.toLong()

        return Race(time, distance).getWinningTimes().size

    }
}

data class Race(val time: Long, val distance: Long) {
    fun getWinningTimes(): List<Long> {
        return (1..<time)
            .filter { (it)*(time-it) > distance }
            .toList()
    }
}