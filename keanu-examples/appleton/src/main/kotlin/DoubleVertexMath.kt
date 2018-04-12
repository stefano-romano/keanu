import io.improbable.keanu.vertices.bool.BoolVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.GreaterThanVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.LessThanOrEqualVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.LessThanVertex
import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary.PowerVertex

class DoubleVertexMath : KeanuMath<DoubleVertex, BoolVertex> {

    override fun pow(a: DoubleVertex, b: Double): DoubleVertex {
        return PowerVertex(a, b)
    }

    override fun lessThan(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanVertex(a, b)
    }

    override fun greaterThan(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return GreaterThanVertex(a, b)
    }

    override fun lessThanOrEqual(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(a, b)
    }

    override fun greaterThanOrEqual(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(a, b)
    }

}
