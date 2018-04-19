package io.improbable.operators.arith

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.pow

class Div(val a: DifferentiableDouble, val b: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
        b.addChild(this)
    }

    override fun updateValue() {
        value = a.value / b.value
    }

    override fun fwdAutoDiff() {
        delta = a.delta / b.value - a.value * b.delta / pow(b.value, 2.0)
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        a.revAutoDiff(this, delta / b.value)
        b.revAutoDiff(this, -a.value * delta / pow(b.value, 2.0))
    }
}
