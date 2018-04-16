import io.improbable.keanu.kotlin.BooleanOperators
import io.improbable.keanu.kotlin.DoubleOperators

interface KeanuMath<DOUBLE : DoubleOperators<DOUBLE>, BOOLEAN_IN, BOOLEAN : BooleanOperators<BOOLEAN_IN, BOOLEAN>> {

    fun pow(a: DOUBLE, b: Double): DOUBLE

    fun lessThan(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun lessThan(a: DOUBLE, b: Double): BOOLEAN

    fun lessThan(a: Double, b: DOUBLE): BOOLEAN

    fun greaterThan(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun greaterThan(a: DOUBLE, b: Double): BOOLEAN

    fun greaterThan(a: Double, b: DOUBLE): BOOLEAN

    fun lessThanOrEqual(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun lessThanOrEqual(a: DOUBLE, b: Double): BOOLEAN

    fun lessThanOrEqual(a: Double, b: DOUBLE): BOOLEAN

    fun greaterThanOrEqual(a: DOUBLE, b: DOUBLE): BOOLEAN

    fun greaterThanOrEqual(a: DOUBLE, b: Double): BOOLEAN

    fun greaterThanOrEqual(a: Double, b: DOUBLE): BOOLEAN

}
