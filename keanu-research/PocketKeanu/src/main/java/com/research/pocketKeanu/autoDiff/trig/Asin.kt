package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import kotlin.math.asin
import kotlin.math.sqrt

class Asin(val a: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = asin(a.value)
    }

    override fun fwdAutoDiff() {
        delta = a.delta / sqrt(1.0 - Math.pow(a.value, 2.0))
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = delta / sqrt(1.0 - Math.pow(a.value, 2.0))
        a.revAutoDiff(this, parentDx)
    }
}
