package day8

import ITaskTest
import mapMultiLineString
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskTest: ITaskTest {

    private val task: Task = Task()

    @Test
    override fun testExampleInput() {
        val input = mapMultiLineString("""
            RL
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent())

        assertEquals(2, task.solve(input))
    }
}