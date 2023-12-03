package day1

import ITaskTest
import mapMultiLineString
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskTest: ITaskTest {

    private val testTask: Task = Task()

    @Test
    override fun testExampleInput() {
        val input = mapMultiLineString(
            """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """
        )

        assertEquals(142, testTask.solve(input))
    }

    @Test
    fun testExampleInputWithDigitsAsWords() {
        val input = mapMultiLineString(
            """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()
        )

        assertEquals(281, testTask.solve(input))
    }

    @Test
    fun testKnownEdgeCases() {
        assertEquals(15, testTask.solve(listOf("tbbffonefhrvrvbzstkn53lbgvmgvxk5")))
    }
}