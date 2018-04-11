import io.improbable.keanu.kotlin.DoubleOperators

/**
 * Let's start with number of apples being continuous then discretise later
 */
class AppleTree<DOUBLE : DoubleOperators<DOUBLE>>(val xLocation: DOUBLE, val yLocation: DOUBLE,
                                                  initialAppleCount: DOUBLE, val appleGrowthRate: DOUBLE) : IAgent {

    var appleCount = initialAppleCount

    override fun step() {
        appleCount += appleGrowthRate
    }


}