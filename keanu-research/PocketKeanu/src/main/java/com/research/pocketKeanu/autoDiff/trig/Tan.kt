package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.tan

class Tan(val a: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = tan(a.value)
    }

    override fun fwdAutoDiff() {
        delta = a.delta / pow(cos(a.value), 2.0)
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = delta / pow(cos(a.value), 2.0)
        a.revAutoDiff(this, parentDx)
    }
}
