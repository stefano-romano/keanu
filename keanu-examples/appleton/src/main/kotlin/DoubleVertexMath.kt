import io.improbable.keanu.vertices.Vertex
import io.improbable.keanu.vertices.bool.BoolVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.GreaterThanVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.LessThanOrEqualVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.LessThanVertex
import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary.PowerVertex

class DoubleVertexMath : KeanuMath<DoubleVertex, Vertex<Boolean>, BoolVertex> {

    override fun pow(a: DoubleVertex, b: Double): DoubleVertex {
        return PowerVertex(a, b)
    }

    override fun lessThan(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanVertex(a, b)
    }

    override fun lessThan(a: DoubleVertex, b: Double): BoolVertex {
        return LessThanVertex(a, ConstantDoubleVertex(b))
    }

    override fun lessThan(a: Double, b: DoubleVertex): BoolVertex {
        return LessThanVertex(ConstantDoubleVertex(a), b)
    }

    override fun greaterThan(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return GreaterThanVertex(a, b)
    }

    override fun greaterThan(a: DoubleVertex, b: Double): BoolVertex {
        return GreaterThanVertex(a, ConstantDoubleVertex(b))
    }

    override fun greaterThan(a: Double, b: DoubleVertex): BoolVertex {
        return GreaterThanVertex(ConstantDoubleVertex(a), b)
    }

    override fun lessThanOrEqual(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(a, b)
    }

    override fun lessThanOrEqual(a: DoubleVertex, b: Double): BoolVertex {
        return LessThanOrEqualVertex(a, ConstantDoubleVertex(b))
    }

    override fun lessThanOrEqual(a: Double, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(ConstantDoubleVertex(a), b)
    }

    override fun greaterThanOrEqual(a: DoubleVertex, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(a, b)
    }

    override fun greaterThanOrEqual(a: DoubleVertex, b: Double): BoolVertex {
        return LessThanOrEqualVertex(a, ConstantDoubleVertex(b))
    }

    override fun greaterThanOrEqual(a: Double, b: DoubleVertex): BoolVertex {
        return LessThanOrEqualVertex(ConstantDoubleVertex(a), b)
    }

}
