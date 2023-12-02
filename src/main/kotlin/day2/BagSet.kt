package day2

data class BagSet(
    val r: Int,
    val g: Int,
    val b: Int
)

fun BagSet.isPossible(total: BagSet): Boolean =
    this.r <= total.r && this.g <= total.g && this.b <= total.b
