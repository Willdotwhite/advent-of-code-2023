package day3

import ITaskTest
import mapMultiLineString
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskTest: ITaskTest {

    private val testTask: Task = Task()

    @Test
    override fun testExampleInput() {
        val input = mapMultiLineString("""
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent())

        assertEquals(4361, testTask.solve(input))
    }
    @Test
    fun testLiteralEdgeCase() {
        val input = mapMultiLineString("""
            467..114..
            ...*......
            ..35...633
            .......#..
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent())

        assertEquals(4361, testTask.solve(input))
    }

    @Test
    fun testRunOnSymbols() {
        val input = mapMultiLineString("""
            4^.....%2.
            1%.....*4.
            3".....2(..
        """.trimIndent())

        assertEquals(16, testTask.solve(input))
    }
}