import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.randomFactory.RandomFactory
import java.util.*

class World<DOUBLE : DoubleOperators<DOUBLE>>(val numTrees: Int, val numScrumpers: Int, val randomFactory: RandomFactory<DOUBLE>) {

    val minX = -10.0
    val maxX = 10.0
    val minY = -10.0
    val maxY = 10.0
    val minStartingApples = 5.0
    val maxStartingApples = 5.0
    val appleGrowthRate = 1.0

    val random = Random()

    val trees = arrayListOf<AppleTree<DOUBLE>>()
    val scrumpers = arrayListOf<AppleScrumper<DOUBLE>>()

    fun step() {

    }

    init {
        for (i in 0..numTrees) {
            trees.add(makeTree())
        }

        for (i in 0..numScrumpers) {

        }
    }

    private fun makeTree(): AppleTree<DOUBLE> {
        return AppleTree(this, nextX(), nextY(), randomFactory.nextDouble(minStartingApples, maxStartingApples), appleGrowthRate)
    }

    private fun makeScrumper() {

    }

    private fun nextX(): Double {
        return minX + random.nextDouble() * (maxX - minX)
    }

    private fun nextY(): Double {
        return minY + random.nextDouble() * (maxY - minY)
    }
}
