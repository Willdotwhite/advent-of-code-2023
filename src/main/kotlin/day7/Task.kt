package day7

import ITask
import kotlin.math.pow

class Task: ITask {
    override fun solve(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(" ") }
            .map { Pair(Hand(it[0]), it[1].toInt()) }
            .sortedBy { it.first.sortableScore() }
            .mapIndexed { index, pair -> Pair(index + 1, pair.second) } // +1 because we're zero indexing here
            .fold(0) { acc, pair -> acc + (pair.first * pair.second) }
    }
}

data class Hand(
    val cardsString: String
) {
    private val cardMap: Map<Card, Int> = cardsString
        .split("").filter { it.isNotBlank() }
        .map { Card.fromString(it) }
        .groupBy { it }
        .map { it.key to it.value.size }
        .toMap()

    private val handType = determineHandTypeWithJokers()

    private fun determineHandTypeWithJokers(): HandType {
        if (cardMap.containsKey(Card.Joker)) {
            val numberOfJokers = cardMap[Card.Joker]!!

            // Create a copy of the hand without Jokers
            val wildCardedHand: MutableMap<Card, Int> = cardMap.filterKeys { it != Card.Joker }.toMutableMap()

            // Replace all jokers with the highest value card;
            // there's no combination where two jokers become two different cards to form a winning hand
            val highestValueCard = if (wildCardedHand.keys.size > 0)
                // Get most numerous card, then highest value for multiple matches
                wildCardedHand.entries.sortedWith(compareBy( { it.value }, { -it.key.score() })).reversed()[0].key
                else Card.Ace

            wildCardedHand[highestValueCard] = wildCardedHand[highestValueCard]?.plus(numberOfJokers) ?: numberOfJokers
            return determineHandType(wildCardedHand)
        }

        return determineHandType(cardMap)
    }

    private fun determineHandType(cardMap: Map<Card, Int>): HandType = when {
        (cardMap.containsValue(5)) -> HandType.FiveOfAKind
        (cardMap.containsValue(4)) -> HandType.FourOfAKind
        (cardMap.containsValue(3) && cardMap.containsValue(2)) -> HandType.FullHouse
        (cardMap.containsValue(3)) -> HandType.ThreeOfAKind
        (cardMap.filter { it.value == 2 }.size == 2) -> HandType.TwoPair
        (cardMap.values.max() == 2) -> HandType.OnePair
        else -> HandType.HighCard
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun sortableScore(): String = this.handType.score().toString() +
            this.cardsString
                .map { char -> Card.fromChar(char).score() }
                .joinToString("") { it.toHexString(format = HexFormat.Default).takeLast(2) }

}

enum class Card(val card: String) {
    Ace("A"),
    King("K"),
    Queen("Q"),
    Joker("J"),
    Ten("T"),
    Nine("9"),
    Eight("8"),
    Seven("7"),
    Six("6"),
    Five("5"),
    Four("4"),
    Three("3"),
    Two("2");

    fun score(): Int = when(card) {
        "A" -> 14
        "K" -> 13
        "Q" -> 12
        "J" -> 1
        "T" -> 10
        else -> card.toInt()
    }

    companion object {
        fun fromString(s: String): Card = Card.entries.first { enum -> enum.card == s }
        fun fromChar(c: Char): Card = Card.fromString(c.toString())
    }
}

enum class HandType(private val score: Int) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    TwoPair(3),
    OnePair(2),
    HighCard(1);

    fun score(): Int = score
}
