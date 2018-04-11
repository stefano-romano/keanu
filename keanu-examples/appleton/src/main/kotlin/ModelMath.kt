import io.improbable.keanu.kotlin.DoubleOperators

interface ModelMath<DOUBLE : DoubleOperators<DOUBLE>> {

    fun pow(a: DOUBLE, b: Double): DOUBLE

}
