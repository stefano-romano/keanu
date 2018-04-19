package io.improbable.operators.trig

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import kotlin.math.cosh
import kotlin.math.sinh

class Sinh(val a: DifferentiableDouble) : DifferentiableDouble() {

    // http://math2.org/math/derivatives/more/hyperbolics.htm

    init {
        a.addChild(this)
    }

    override fun updateValue() {
        value = sinh(a.value)
    }

    override fun fwdAutoDiff() {
        delta = cosh(a.value) * a.delta
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        val parentDx = cosh(a.value) * delta
        a.revAutoDiff(this, parentDx)
    }
}
