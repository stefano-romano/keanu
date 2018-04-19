package io.improbable.operators.arith

import com.research.pocketKeanu.autoDiff.DifferentiableDouble

class Times(val a: DifferentiableDouble, val b: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
        b.addChild(this)
    }

    override fun updateValue() {
        value = a.value * b.value
    }

    override fun fwdAutoDiff() {
        delta = b.value * a.delta + a.value * b.delta
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        a.revAutoDiff(this, b.value * this.delta)
        b.revAutoDiff(this, a.value * this.delta)
    }
}
