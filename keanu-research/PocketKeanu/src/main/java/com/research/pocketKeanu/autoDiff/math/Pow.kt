package io.improbable.operators.math

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.log
import java.lang.Math.pow

class Pow(val a: DifferentiableDouble, val b: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
        b.addChild(this)
    }

    override fun updateValue() {
        value = pow(a.value, b.value)
    }

    override fun fwdAutoDiff() {
        delta = (b.value * pow(a.value, b.value - 1.0) * a.delta)  + (log(a.value) * pow(a.value, b.value) * b.delta)
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        a.revAutoDiff(this, b.value * pow(a.value, b.value - 1.0) * delta)
        b.revAutoDiff(this, log(a.value) * pow(a.value, b.value) * delta)
    }
}
