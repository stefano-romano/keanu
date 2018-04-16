import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.randomFactory.RandomFactory
import java.util.*
import io.improbable.keanu.kotlin.*

class World<DOUBLE : DoubleOperators<DOUBLE>, BOOLEAN_IN, BOOLEAN : BooleanOperators<BOOLEAN_IN, BOOLEAN>>(
        val numTrees: Int, val numScrumpers: Int, val randomFactory: RandomFactory<DOUBLE>,
        val math: KeanuMath<DOUBLE, BOOLEAN_IN, BOOLEAN>, val flow: KeanuControlFlow<BOOLEAN_IN, BOOLEAN>) {

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

    val trees = arrayListOf<AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>>()
    val scrumpers = arrayListOf<AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>>()

    init {
        for (i in 0..numTrees) {
            trees.add(makeTree())
        }

        for (i in 0..numScrumpers) {
            scrumpers.add(makeScrumper())
        }
    }

    fun run(steps: Int) {
        for (i in 1..steps) {
            println("Step $i")
            step()
        }
    }

    fun step() {
        trees.forEach(AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>::step)
        scrumpers.forEach(AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>::step)
    }

    fun lookAround(x: DOUBLE, y: DOUBLE, range: Double): World<DOUBLE, BOOLEAN_IN, BOOLEAN>.Perception {
//        val treesByDistance = TreeMap<DOUBLE, AppleTree<DOUBLE>>()
//        for (tree in trees) {
//            val xDist = tree.xLocation - x
//            val yDist = tree.yLocation - y
//            val dist = math.pow((xDist * xDist) + (yDist * yDist), 0.5)
//
//        }

        return Perception(trees, scrumpers)
    }

    private fun makeTree(): AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN> {
        return AppleTree(this, nextX(), nextY(), randomFactory.nextDouble(minStartingApples, maxStartingApples), appleGrowthRate)
    }

    private fun makeScrumper(): AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN> {
        val x = randomFactory.nextDouble(minX, maxX)
        val y = randomFactory.nextDouble(minY, maxY)
        val appleStock = randomFactory.nextDouble(minStartingApples, maxStartingApples)
        val scrumperType = getRandomScrumperType()
        val aggression = randomFactory.nextDouble(minAggression, maxAggression)

        return AppleScrumper(this, x, y, appleStock, aggression, scrumperType, math, flow)
    }

    private fun nextX(): Double {
        return minX + random.nextDouble() * (maxX - minX)
    }

    private fun nextY(): Double {
        return minY + random.nextDouble() * (maxY - minY)
    }

    private fun treesInRange(x: DOUBLE, y: DOUBLE, range: Double): TreeMap<DOUBLE, AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>> {
        val treesByDistance = TreeMap<DOUBLE, AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>>()
        for (tree in trees) {
            val xDist = x - tree.xLocation
            val yDist = y - tree.yLocation
            val inverseDist = 1.0 / math.pow((xDist * xDist) + (yDist * yDist), 0.5)

        }

        return treesByDistance
    }

    private fun getRandomScrumperType(): ScrumperTypes {
        when (random.nextInt(3)) {
            0 -> return ScrumperTypes.EARTHLING
            1 -> return ScrumperTypes.MARTIAN
            2 -> return ScrumperTypes.VENUSIAN
        }
        return ScrumperTypes.EARTHLING
    }

    inner class Perception(val appleTrees : ArrayList<AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>>,
                           val appleScrumpers : ArrayList<AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>>)
}
