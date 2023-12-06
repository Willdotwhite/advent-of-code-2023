package day4

import ITask

class Task: ITask {
    val cardLookUp: MutableMap<Int, Card> = mutableMapOf()

    override fun solve(input: List<String>): Int {

        val cards = input
            .map { string ->
                val (id, winningNumbers, scoringNumbers) = """^Card\s+(\d+): ([\d| ]+) \| ([\d| ]+)$""".toRegex()
                    .find(string)!!.destructured

                Card(
                    id.toInt(),
                    winningNumbers.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toList(),
                    scoringNumbers.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toList()
                )
            }
            .also {
                it.map { card -> cardLookUp[card.id] = card }
            }

        var cardsToSettle = mutableListOf<Card>()
        cardsToSettle.addAll(cards)

        var totalCards = 0

        var anyCardsWereGenerated = true
        while (anyCardsWereGenerated) {
            val newCards = mutableListOf<Card>()

            for (card in cardsToSettle) {
                totalCards += 1
                newCards.addAll(card.resolve(cardLookUp))
            }

            anyCardsWereGenerated = newCards.size > 0
            cardsToSettle = newCards
        }


        return totalCards
    }
}

data class Card(val id: Int, val winningNumbers: List<Int>, val scoringNumbers: List<Int>) {
    fun resolve(cardLookUp: Map<Int, Card>): List<Card> {
        val numberOfWins = scoringNumbers.filter { winningNumbers.contains(it) }.size
        return (id + 1..id + numberOfWins).map { cardLookUp[it]!! }.toList()
    }
}