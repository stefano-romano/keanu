import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.randomFactory.RandomFactory
import java.util.*
import io.improbable.keanu.kotlin.*

class World<DOUBLE : DoubleOperators<DOUBLE>>(val numTrees: Int, val numScrumpers: Int, val randomFactory: RandomFactory<DOUBLE>, val math: ModelMath<DOUBLE>) {

    val minX = -10.0
    val maxX = 10.0
    val minY = -10.0
    val maxY = 10.0
    val minStartingApples = 5.0
    val maxStartingApples = 5.0
    val minAggression = 0.0
    val maxAggression = 1.0
    val appleGrowthRate = 1.0

    val random = Random()

    val trees = arrayListOf<AppleTree<DOUBLE>>()
    val scrumpers = arrayListOf<AppleScrumper<DOUBLE>>()

    init {
        for (i in 0..numTrees) {
            trees.add(makeTree())
        }

        for (i in 0..numScrumpers) {
            scrumpers.add(makeScrumper())
        }
    }

    fun step() {

    }

    fun lookAround(x: DOUBLE, y: DOUBLE, range: Double): World<DOUBLE>.Perception {
        val treesByDistance = TreeMap<DOUBLE, AppleTree<DOUBLE>>()
        for (tree in trees) {
            val xDist = tree.xLocation - x
            val yDist = tree.yLocation - y
            val dist = math.pow((xDist * xDist) + (yDist * yDist), 0.5)

        }

        Perception(arrayListOf(), arrayListOf())
    }

    private fun makeTree(): AppleTree<DOUBLE> {
        return AppleTree(this, nextX(), nextY(), randomFactory.nextDouble(minStartingApples, maxStartingApples), appleGrowthRate)
    }

    private fun makeScrumper(): AppleScrumper<DOUBLE> {
        val x = randomFactory.nextDouble(minX, maxX)
        val y = randomFactory.nextDouble(minY, maxY)
        val appleStock = randomFactory.nextDouble(minStartingApples, maxStartingApples)
        val aggression = randomFactory.nextDouble(minAggression, maxAggression)
        return AppleScrumper(x, y, appleStock, aggression)
    }

    private fun nextX(): Double {
        return minX + random.nextDouble() * (maxX - minX)
    }

    private fun nextY(): Double {
        return minY + random.nextDouble() * (maxY - minY)
    }

    private fun treesInRange(x: DOUBLE, y: DOUBLE, range: Double): TreeMap<DOUBLE, AppleTree<DOUBLE>> {
        val treesByDistance = TreeMap<DOUBLE, AppleTree<DOUBLE>>()
        for (tree in trees) {
            val xDist = x - tree.xLocation
            val yDist = y - tree.yLocation
            val inverseDist = 1.0 / math.pow((xDist * xDist) + (yDist * yDist), 0.5)

        }

        return treesByDistance
    }

    inner class Perception(val appleTrees : ArrayList<AppleTree<DOUBLE>>,
                           val appleScrumpers : ArrayList<AppleScrumper<DOUBLE>>)
}
