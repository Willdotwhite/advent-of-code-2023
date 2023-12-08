package day7

import ITaskTest
import mapMultiLineString
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskTest: ITaskTest {

    private val task: Task = Task()

    @Test
    override fun testExampleInput() {
        val input = mapMultiLineString("""
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent())

        assertEquals(6440, task.solve(input))
    }
}