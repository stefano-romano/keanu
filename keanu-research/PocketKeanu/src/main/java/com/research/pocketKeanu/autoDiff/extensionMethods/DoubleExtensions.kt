package io.improbable.operators.extensionMethods

import com.research.pocketKeanu.autoDiff.DifferentiableDouble


operator fun Double.plus(that: DifferentiableDouble): DifferentiableDouble {
    return DifferentiableDouble(this) + that
}

operator fun Double.minus(that: DifferentiableDouble): DifferentiableDouble {
    return DifferentiableDouble(this) - that
}

operator fun Double.times(that: DifferentiableDouble): DifferentiableDouble {
    return DifferentiableDouble(this) * that
}

operator fun Double.div(that: DifferentiableDouble): DifferentiableDouble {
    return DifferentiableDouble(this) / that
}
