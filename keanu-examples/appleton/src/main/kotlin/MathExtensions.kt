import io.improbable.keanu.kotlin.ArithmeticDouble
import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary.PowerVertex

fun Math.pow(a: DoubleVertex, b: Double): DoubleVertex {
    return PowerVertex(a, b)
}

fun Math.pow(a: ArithmeticDouble, b: Double): ArithmeticDouble {
    return ArithmeticDouble(Math.pow(a.value, b))
}
