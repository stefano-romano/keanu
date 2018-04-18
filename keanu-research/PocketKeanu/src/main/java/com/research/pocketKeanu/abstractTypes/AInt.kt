package com.research.pocketKeanu.abstractTypes

class AInt(val value: Int) : IntLike<AInt> {

    override fun minus(that: AInt): AInt {
        return AInt(value - that.value)
    }

    override fun plus(that: AInt): AInt {
        return AInt(value + that.value)
    }

    override fun times(that: AInt): AInt {
        return AInt(value * that.value)
    }

    override fun div(that: AInt): AInt {
        return AInt(value / that.value)
    }

    override fun minus(value: Int): AInt {
        return AInt(this.value - value)
    }

    override fun plus(value: Int): AInt {
        return AInt(this.value + value)
    }

    override fun times(value: Int): AInt {
        return AInt(this.value * value)
    }

    override fun div(value: Int): AInt {
        return AInt(this.value / value)
    }

    override fun unaryMinus(): AInt {
        return AInt(value) * -1
    }

}
