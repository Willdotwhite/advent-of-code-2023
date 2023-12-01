import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskTest {

    private val testTask: Task = Task()

    @Test
    fun testExampleInput() {
        val input = mapMultiLineString("""
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """)

        assertEquals(142, testTask.solve(input))
    }
}