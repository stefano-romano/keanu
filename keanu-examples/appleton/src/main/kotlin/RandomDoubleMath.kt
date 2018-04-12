import io.improbable.keanu.kotlin.ArithmeticBoolean
import io.improbable.keanu.kotlin.ArithmeticDouble

class RandomDoubleMath : KeanuMath<ArithmeticDouble, ArithmeticBoolean> {

    override fun pow(a: ArithmeticDouble, b: Double): ArithmeticDouble {
        return ArithmeticDouble(Math.pow(a.value, b))
    }

    override fun lessThan(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value < b.value)
    }

    override fun greaterThan(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value > b.value)
    }

    override fun lessThanOrEqual(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value <= b.value)
    }

    override fun greaterThanOrEqual(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value >= b.value)
    }

}
