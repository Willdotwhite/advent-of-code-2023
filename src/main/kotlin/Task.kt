class Task {

    fun solve(input: List<String>): Int {
        return input
            .map { it.replace(Regex("\\D"), "") }
            .map { "${it.first()}${it.last()}".toInt() }
            .reduce { acc, sum -> acc + sum }
    }

}