import io.improbable.keanu.kotlin.BooleanOperators
import io.improbable.keanu.kotlin.DoubleOperators

interface KeanuMath<DOUBLE : DoubleOperators<DOUBLE>, BOOLEAN : BooleanOperators<BOOLEAN>> {

    fun pow(a: DOUBLE, b: Double): DOUBLE

    fun lessThan(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun greaterThan(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun lessThanOrEqual(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun greaterThanOrEqual(a: DOUBLE, b: DOUBLE): BOOLEAN

}
