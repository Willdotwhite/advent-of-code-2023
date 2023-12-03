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
            ...$.*....
            .664.598..
        """.trimIndent())

        assertEquals(467835, testTask.solve(input))
    }

}