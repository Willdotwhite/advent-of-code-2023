package day2

class Task {

    fun solve(input: List<String>, totalCubesInBagSet: BagSet): Int {
        return input
            .map {
                val (id, draws) = Regex("^Game (\\d+): (.*)$").find(it)!!.destructured
                Pair(id, parseDrawsToBagSet(draws))
            }
            .filter {
                // None of the games violate the rules
                it.second.all { bagSet -> bagSet.isPossible(totalCubesInBagSet) }
            }
            .sumOf { it.first.toInt() }
    }

    private fun parseDrawsToBagSet(drawString: String): List<BagSet> {
        fun String.parseToInt(colour: String) = Regex("(\\d+) $colour")
            .find(this)?.groupValues?.get(1)?.toInt() ?: 0

        val bagSets = mutableListOf<BagSet>()
        drawString
            .split(";")
            .map {
                BagSet(it.parseToInt("red"), it.parseToInt("green"), it.parseToInt("blue"))
            }
            .map { bagSets.add(it) }

        return bagSets
    }

}

typealias BagSet = Triple<Int, Int, Int>
fun BagSet.isPossible(total: BagSet): Boolean =
    this.first <= total.first && this.second <= total.second && this.third <= total.third
