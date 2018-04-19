package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import kotlin.math.atan

class Atan(val a: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = atan(a.value)
    }

    override fun fwdAutoDiff() {
        delta = a.delta / (1.0 + Math.pow(a.value, 2.0))
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = delta / (1.0 + Math.pow(a.value, 2.0))
        a.revAutoDiff(this, parentDx)
    }
}
