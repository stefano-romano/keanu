package io.improbable.operators.arith

import com.research.pocketKeanu.autoDiff.DifferentiableDouble

class Minus(val a: DifferentiableDouble, val b: DifferentiableDouble) : DifferentiableDouble() {

    init {
        a.addChild(this)
        b.addChild(this)
    }

    override fun updateValue() {
        value = a.value - b.value
    }

    override fun fwdAutoDiff() {
        delta = a.delta - b.delta
        propagateFwdAutoDiff()
    }

    override fun propagateRevAutoDiff() {
        a.revAutoDiff(this, this.delta)
        b.revAutoDiff(this, -this.delta)
    }
}
