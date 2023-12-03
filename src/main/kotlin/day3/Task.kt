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
                allNumberSpans.add(currentNumberSpan)
                currentNumberSpan = mutableListOf()

                if (cell != '.') {
                    allEngineSymbols.add(Vector2(x, y))
                }

            }

            // If we ended the row with a non-empty number span, save that before moving on
            if (currentNumberSpan.isNotEmpty()) {
                allNumberSpans.add(currentNumberSpan)
            }
        }

        return allNumberSpans
            .filter {  it.isAdjacentToAnySymbol(allEngineSymbols) }
            .map { it.fold("") { acc, (value, _) -> acc + value } }
            .sumOf { it.toInt() }
    }
}

data class Vector2(val x: Int, val y: Int)

typealias EngineSymbol = Vector2
typealias NumberSpan = MutableList<Pair<Int, Vector2>>

fun NumberSpan.isAdjacentToAnySymbol(symbols: List<EngineSymbol>): Boolean {
    return symbols.any { symbol -> this.isAdjacentToSymbol(symbol) }
}
fun NumberSpan.isAdjacentToSymbol(symbol: EngineSymbol): Boolean {
    // There may be a way to optimise this, but we'll brute force for now
    return this.any { (_, coords) -> abs(coords.x - symbol.x) <= 1 && abs(coords.y - symbol.y) <= 1 }
}