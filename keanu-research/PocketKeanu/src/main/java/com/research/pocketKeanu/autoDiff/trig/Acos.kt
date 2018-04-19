package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.pow
import kotlin.math.acos
import kotlin.math.sqrt

class Acos(val a: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = acos(a.value)
    }

    override fun fwdAutoDiff() {
        if (a.value == 1.0) {
            println("A = ${a.value}")
        }
        delta = -a.delta / sqrt(1.0 - pow(a.value, 2.0))
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = -delta / sqrt(1.0 - pow(a.value, 2.0))
        a.revAutoDiff(this, parentDx)
    }
}
