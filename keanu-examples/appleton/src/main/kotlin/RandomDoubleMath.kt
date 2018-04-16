import io.improbable.keanu.kotlin.ArithmeticBoolean
import io.improbable.keanu.kotlin.ArithmeticDouble

class RandomDoubleMath : KeanuMath<ArithmeticDouble, ArithmeticBoolean, ArithmeticBoolean> {

    override fun pow(a: ArithmeticDouble, b: Double): ArithmeticDouble {
        return ArithmeticDouble(Math.pow(a.value, b))
    }

    override fun lessThan(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value < b.value)
    }

    override fun lessThan(a: ArithmeticDouble, b: Double): ArithmeticBoolean {
        return ArithmeticBoolean(a.value < b)
    }

    override fun lessThan(a: Double, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a < b.value)
    }

    override fun greaterThan(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value > b.value)
    }

    override fun greaterThan(a: ArithmeticDouble, b: Double): ArithmeticBoolean {
        return ArithmeticBoolean(a.value > b)
    }

    override fun greaterThan(a: Double, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a > b.value)
    }

    override fun lessThanOrEqual(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value <= b.value)
    }

    override fun lessThanOrEqual(a: ArithmeticDouble, b: Double): ArithmeticBoolean {
        return ArithmeticBoolean(a.value <= b)
    }

    override fun lessThanOrEqual(a: Double, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a <= b.value)
    }

    override fun greaterThanOrEqual(a: ArithmeticDouble, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a.value >= b.value)
    }

    override fun greaterThanOrEqual(a: ArithmeticDouble, b: Double): ArithmeticBoolean {
        return ArithmeticBoolean(a.value >= b)
    }

    override fun greaterThanOrEqual(a: Double, b: ArithmeticDouble): ArithmeticBoolean {
        return ArithmeticBoolean(a >= b.value)
    }
}
