import io.improbable.keanu.kotlin.ArithmeticDouble

class RandomDoubleMath: ModelMath<ArithmeticDouble> {

    override fun pow(a: ArithmeticDouble, b: Double): ArithmeticDouble {
        return ArithmeticDouble(Math.pow(a.value, b))
    }

}
