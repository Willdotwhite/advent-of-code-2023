package day2

import ITask

class Task: ITask {

    override fun solve(input: List<String>): Int {
        return input
            .map {
                val (id, draws) = Regex("^Game (\\d+): (.*)$").find(it)!!.destructured
                Pair(id, parseDrawsToBagSet(draws))
            }
            .map {
                // Create one BagSet with the highest R/G/B numbers seen
                (_, sets) -> BagSet(
                    sets.maxBy { it.r }.r,
                    sets.maxBy { it.g }.g,
                    sets.maxBy { it.b }.b,
                )
            }
            .fold(0) {
                acc: Int, bagSet: BagSet -> acc + (bagSet.r * bagSet.g * bagSet.b)
            }
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
