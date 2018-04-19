package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import java.lang.Math.pow
import kotlin.math.tanh

class Tanh(val a: DifferentiableDouble) : DifferentiableDouble() {

    // http://math2.org/math/derivatives/more/hyperbolics.htm

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = tanh(a.value)
    }

    override fun fwdAutoDiff() {
        delta = (1.0 - pow(tanh(a.value), 2.0)) * a.delta
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = (1.0 - pow(tanh(a.value), 2.0)) * delta
        a.revAutoDiff(this, parentDx)
    }
}
