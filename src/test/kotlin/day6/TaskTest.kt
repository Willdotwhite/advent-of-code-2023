package day6

import ITaskTest
import mapMultiLineString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TaskTest: ITaskTest {

    private val task: Task = Task()

    @Test
    override fun testExampleInput() {
        val input = mapMultiLineString("""
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent())

        assertEquals(288, task.solve(input))
    }
}