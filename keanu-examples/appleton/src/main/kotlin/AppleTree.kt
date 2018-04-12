import io.improbable.keanu.kotlin.BooleanOperators
import io.improbable.keanu.kotlin.DoubleOperators

/**
 * Let's start with number of apples being continuous then discretise later
 */
class AppleTree<DOUBLE: DoubleOperators<DOUBLE>, BOOLEAN : BooleanOperators<BOOLEAN>>(
        val world: World<DOUBLE, BOOLEAN>, val xLocation: Double, val yLocation: Double, var appleCount: DOUBLE,
        val appleGrowthRate: Double) : IAgent {


    override fun step() {
        appleCount += appleGrowthRate
        println("GROW!")
    }

}
