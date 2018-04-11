package io.improbable.keanu.kotlin

import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex


operator fun Double.plus(that: DoubleVertex): DoubleVertex {
    return that + this
}

operator fun Double.minus(that: DoubleVertex): DoubleVertex {
    return ConstantDoubleVertex(this) - that
}

operator fun Double.times(that: DoubleVertex): DoubleVertex {
    return that * this
}

operator fun Double.div(that: DoubleVertex): DoubleVertex {
    return ConstantDoubleVertex(this) / that
}

operator fun Double.plus(that: ArithmeticDouble): ArithmeticDouble {
    return that + this
}

operator fun Double.minus(that: ArithmeticDouble): ArithmeticDouble {
    return ArithmeticDouble(this) - that
}

operator fun Double.times(that: ArithmeticDouble): ArithmeticDouble {
    return ArithmeticDouble(this) - that
}

operator fun Double.div(that: ArithmeticDouble): ArithmeticDouble {
    return ArithmeticDouble(this) / that
}

operator fun <DOUBLE> Double.plus(that: DoubleOperators<DOUBLE>): DOUBLE {
    if (that is DoubleVertex) {
        return plus(that as DoubleVertex) as DOUBLE
    }

    return plus(that as ArithmeticDouble) as DOUBLE
}

operator fun <DOUBLE> Double.minus(that: DoubleOperators<DOUBLE>): DOUBLE {
    if (that is DoubleVertex) {
        return minus(that as DoubleVertex) as DOUBLE
    }

    return minus(that as ArithmeticDouble) as DOUBLE
}

operator fun <DOUBLE> Double.times(that: DoubleOperators<DOUBLE>): DOUBLE {
    if (that is DoubleVertex) {
        return times(that as DoubleVertex) as DOUBLE
    }

    return times(that as ArithmeticDouble) as DOUBLE
}

operator fun <DOUBLE> Double.div(that: DoubleOperators<DOUBLE>): DOUBLE {
    if (that is DoubleVertex) {
        return div(that as DoubleVertex) as DOUBLE
    }

    return div(that as ArithmeticDouble) as DOUBLE
}
