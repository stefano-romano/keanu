package io.improbable.operators.math

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.log
import java.lang.Math.pow

class Log(val x: DifferentiableDouble) : DifferentiableDouble() {

    init {
        x.addChild(this)
    }

    override fun updateValue() {
        value = log(x.value)
    }

    override fun fwdAutoDiff() {
        delta = x.delta / x.value
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        x.revAutoDiff(this, delta / x.value)
    }
}
