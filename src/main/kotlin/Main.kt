import kotlin.time.measureTimedValue

fun main() {
    val task = day6.Task()
    val input = mapMultiLineString(getInput())

    val (solution, timeTaken) = measureTimedValue {
        task.solve(input)
    }

    val formattedTime = "%,d".format(timeTaken.inWholeNanoseconds / 1000)
    println("Answer: $solution (time: $formattedTime μs)")
}

fun mapMultiLineString(input: String) = input.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

fun getInput(): String {
    return """
Time:        48     98     90     83
Distance:   390   1103   1112   1360
        """
}