package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import kotlin.math.cos
import kotlin.math.sin

class Cos(val a: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = cos(a.value)
    }

    override fun fwdAutoDiff() {
        delta = -sin(a.value) * a.delta
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = -sin(a.value) * delta
        a.revAutoDiff(this, parentDx)
    }
}
