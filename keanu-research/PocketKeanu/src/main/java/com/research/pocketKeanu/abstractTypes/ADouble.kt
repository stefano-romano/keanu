package com.research.pocketKeanu.abstractTypes

class ADouble(val value: Double) : DoubleLike<ADouble> {

    override fun minus(that: ADouble): ADouble {
        return ADouble(value - that.value)
    }

    override fun plus(that: ADouble): ADouble {
        return ADouble(value + that.value)
    }

    override fun times(that: ADouble): ADouble {
        return ADouble(value * that.value)
    }

    override fun div(that: ADouble): ADouble {
        return ADouble(value / that.value)
    }

    override fun minus(value: Double): ADouble {
        return ADouble(this.value - value)
    }

    override fun plus(value: Double): ADouble {
        return ADouble(this.value + value)
    }

    override fun times(value: Double): ADouble {
        return ADouble(this.value * value)
    }

    override fun div(value: Double): ADouble {
        return ADouble(this.value / value)
    }

    override fun unaryMinus(): ADouble {
        return this * -1.0
    }

    override fun toString() : String {
        return value.toString()
    }
}
