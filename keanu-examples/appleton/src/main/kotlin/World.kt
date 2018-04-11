import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.randomFactory.RandomDoubleFactory
import io.improbable.keanu.randomFactory.RandomFactory

class World<DOUBLE : DoubleOperators<DOUBLE>>(val numTrees: Int, val numScrumpers: Int, val randomFactory: RandomFactory<DOUBLE>) {

    val minX = -10.0
    val maxX = 10.0
    val minY = -10.0
    val maxY = 10.0
    val min

    val trees = arrayListOf<AppleTree<DOUBLE>>()
    val scrumpers = arrayListOf<AppleScrumper<DOUBLE>>()

    fun step() {

    }

    init {
        for (i in 0..numTrees) {
            trees.add(AppleTree<DOUBLE>())
        }

        for (i in 0..numScrumpers) {

        }
    }

    private fun makeTree() {
        AppleTree<DOUBLE>(randomFactory.nextDouble(minX, maxX), randomFactory.nextDouble(minY, maxY), )
    }

    private fun makeScrumper() {
        randomFactory.
    }

    private fun getRandomX(): DOUBLE {
        if (randomFactory is RandomDoubleFactory) {
            randomFactory.
        }

        if (randomFactory is RandomDoubleFactory) {

        }
    }
}
