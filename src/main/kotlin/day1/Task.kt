package day1

import ITask

class Task: ITask{

    override fun solve(input: List<String>): Int {
        return input
            .map { getDigitPair(it) }
            .sumOf { "${it.first}${it.second}".toInt() }
    }

    private fun getDigitPair(line: String): Pair<String, String> {
        val ltrMapping = mapOf(
            "one" to "1", "two" to "2", "three" to "3",
            "four" to "4", "five" to "5", "six" to "6",
            "seven" to "7", "eight" to "8", "nine" to "9"
        )

        val rltMapping = mapOf(
            "eno" to "1", "owt" to "2", "eerht" to "3",
            "ruof" to "4", "evif" to "5", "xis" to "6",
            "neves" to "7", "thgie" to "8", "enin" to "9"
        )

        return Pair(
            getFirstDigitFromLine(line, ltrMapping),
            getFirstDigitFromLine(line.reversed(), rltMapping)
        )
    }

    private fun getFirstDigitFromLine(line: String, mapping: Map<String, String>): String {
        // Create a Set of the starting characters of each target word in the search
        val startCharsOfNumericWords = mapping.keys
            .map { it.first() }
            .toSet()

        var currentRead = ""

        // Traverse the entire line, return digit version of numeric words (e.g. "2" for "two" or "owt") when found
        for (i in line.indices) {
            val char = line[i]

            // If we've run into an actual digit, then we're golden!
            if (char.isDigit()) {
                return char.toString()
            }

            // If the character being read isn't possibly the start of a target word,
            // continue until the next possible start character
            if (currentRead.isEmpty() && !startCharsOfNumericWords.contains(char)) {
                continue
            }

            currentRead += char

            // If the new string is a valid key, return the digit value
            if (mapping.containsKey(currentRead)) {
                return mapping[currentRead]!!
            }

            // If the next character doesn't append to this one and still create a valid number,
            // drop the first character from the current read and start again from the next character
            while (currentRead.isNotEmpty()) {
                val potentialWordsBeingSpelled = mapping.keys
                    .filter { it.startsWith(currentRead + line[i + 1]) }
                    .toList()

                // The currentRead might contain an overlap of two valid terms (e.g. "fon"),
                // where "fo" is a valid start, "fon" is invalid, but we can't drop the entire read
                // without losing "on" for "one"
                if (potentialWordsBeingSpelled.isEmpty()) {
                    currentRead = currentRead.drop(1)
                } else {
                    // If we might be spelling a valid work, exit the loop to continue the search
                    break
                }
            }

        }

        // We shouldn't get here
        throw Exception("Having a bad day")
    }

}