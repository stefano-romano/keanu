import io.improbable.keanu.kotlin.ArithmeticBoolean
import io.improbable.keanu.kotlin.ArithmeticDouble
import io.improbable.keanu.randomFactory.DoubleVertexFactory
import io.improbable.keanu.randomFactory.RandomDoubleFactory
import io.improbable.keanu.vertices.bool.BoolVertex
import io.improbable.keanu.vertices.dbl.DoubleVertex

fun main(args: Array<String>) {
//    Simulation().runSimple()
    Simulation().runProbabilistic()
}

class Simulation() {

    private val numTrees = 10
    private val numScrumpers = 10

    fun runSimple() {
        val world = World<ArithmeticDouble, ArithmeticBoolean>(numTrees, numScrumpers, RandomDoubleFactory(), RandomDoubleMath())
        world.run(10)
    }

    fun runProbabilistic() {
        val world = World<DoubleVertex, BoolVertex>(numTrees, numScrumpers, DoubleVertexFactory(), DoubleVertexMath())
        world.run(10)
    }
}
