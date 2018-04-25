package io.improbable.keanu.kotlin


class ArithmeticDouble(val value: Double) : DoubleOperators<ArithmeticDouble> {
     fun exp(): ArithmeticDouble {
        return ArithmeticDouble(Math.exp(this.value))
    }

     fun pow(that: ArithmeticDouble): ArithmeticDouble {
        return ArithmeticDouble(Math.pow(this.value, that.value))
    }

     fun pow(value: Double): ArithmeticDouble {
        return ArithmeticDouble(Math.pow(this.value, value))
    }

     fun log(): ArithmeticDouble {
        return ArithmeticDouble(Math.log(this.value))
    }

     fun sin(): ArithmeticDouble {
        return ArithmeticDouble(Math.sin(this.value))
    }

     fun cos(): ArithmeticDouble {
        return ArithmeticDouble(Math.cos(this.value))
    }

    fun asin(): ArithmeticDouble {
        return ArithmeticDouble(Math.asin(this.value))
    }

    fun acos(): ArithmeticDouble {
        return ArithmeticDouble(Math.acos(this.value))
    }

    override fun minus(that: ArithmeticDouble): ArithmeticDouble {
        return ArithmeticDouble(value - that.value)
    }

    override fun plus(that: ArithmeticDouble): ArithmeticDouble {
        return ArithmeticDouble(value + that.value)
    }

    override fun times(that: ArithmeticDouble): ArithmeticDouble {
        return ArithmeticDouble(value * that.value)
    }

    override fun div(that: ArithmeticDouble): ArithmeticDouble {
        return ArithmeticDouble(value / that.value)
    }

    override fun minus(value: Double): ArithmeticDouble {
        return ArithmeticDouble(this.value - value)
    }

    override fun plus(value: Double): ArithmeticDouble {
        return ArithmeticDouble(this.value + value)
    }

    override fun times(value: Double): ArithmeticDouble {
        return ArithmeticDouble(this.value * value)
    }

    override fun div(value: Double): ArithmeticDouble {
        return ArithmeticDouble(this.value / value)
    }

    fun unaryMinus(): ArithmeticDouble {
        return this * -1.0
    }

}
