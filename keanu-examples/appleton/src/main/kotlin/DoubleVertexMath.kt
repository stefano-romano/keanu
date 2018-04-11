import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary.PowerVertex

class DoubleVertexMath: ModelMath<DoubleVertex> {

    override fun pow(a: DoubleVertex, b: Double): DoubleVertex {
        return PowerVertex(a, b)
    }

}
