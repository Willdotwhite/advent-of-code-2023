package day3

import ITask
import kotlin.math.abs

class Task: ITask {
    override fun solve(input: List<String>): Int {
        val allNumberSpans = mutableListOf<NumberSpan>()
        val allEngineSymbols = mutableListOf<EngineSymbol>()

        // Convert List<String> which look something like "467..114.*" into a 2d grid of Char
        val charGrid: Array<CharArray> = input
            .map { line -> line.toCharArray() }
            .toTypedArray()


        for (y in charGrid.indices) {
            val row = charGrid[y]
            var currentNumberSpan: NumberSpan = mutableListOf()

            for (x in row.indices) {
                val cell = row[x]

                if (cell.isDigit()) {
                    currentNumberSpan.add(Pair(cell.digitToInt(), Vector2(x, y)))
                    continue
                }

                // If we have a NumberSpan in progress, and we've hit a non-digit char,
                // track that and then handle the new char
                if (currentNumberSpan.isNotEmpty()) {
                    allNumberSpans.add(currentNumberSpan)
                    currentNumberSpan = mutableListOf()
                }

                if (cell != '.') {
                    allEngineSymbols.add(Vector2(x, y))
                }

            }

            // If we ended the row with a non-empty number span, save that before moving on
            if (currentNumberSpan.isNotEmpty()) {
                allNumberSpans.add(currentNumberSpan)
            }
        }

        val gears = allEngineSymbols
            .asSequence()
            .map { it.getAdjacentNumberSpans(allNumberSpans) }
            .filter { it.isGear() } // TODO What should my naming convention be here?
            .map { gearSpans -> gearSpans.map {span -> span.toInt() }.reduce(Int::times) }
            .toList()

        return gears.sum()
    }
}

data class Vector2(val x: Int, val y: Int)

typealias EngineSymbol = Vector2
typealias NumberSpan = MutableList<NumberSpanChar>
typealias NumberSpanChar = Pair<Int, Vector2>

private fun List<NumberSpan>.isGear(): Boolean {
    return this.size == 2
}
fun NumberSpan.toInt(): Int {
    return this.fold("") { acc, (value, _) -> acc + value }.toInt()
}

private fun EngineSymbol.getAdjacentNumberSpans(numberSpans: List<NumberSpan>): List<NumberSpan> {
    return numberSpans.filter { numberSpan -> this.isAdjacentToNumberSpan(numberSpan) }.toList()
}

private fun EngineSymbol.isAdjacentToNumberSpan(numberSpan: NumberSpan): Boolean {
    // There may be a way to optimise this, but we'll brute force for now
    return numberSpan.any { (_, coords) -> abs(coords.x - this.x) <= 1 && abs(coords.y - this.y) <= 1 }
}