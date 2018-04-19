package com.research.pocketKeanu.autoDiff

import com.research.pocketKeanu.abstractTypes.DoubleLike
import io.improbable.operators.arith.Div
import io.improbable.operators.arith.Minus
import io.improbable.operators.arith.Plus
import io.improbable.operators.arith.Times
import io.improbable.operators.math.Log

// Good resource for understanding forward and reverse mode automatic-differentiation:
// https://rufflewind.com/2016-12-30/reverse-mode-automatic-differentiation

open class DifferentiableDouble : DoubleLike<DifferentiableDouble> {
    override fun log(): DifferentiableDouble {
        return Log(this)
    }

    override fun plus(value: Double): DifferentiableDouble {
        return plus(DifferentiableDouble(value))
    }

    override fun times(value: Double): DifferentiableDouble {
        return times(DifferentiableDouble(value))
    }

    override fun div(value: Double): DifferentiableDouble {
        return div(DifferentiableDouble(value))
    }

    override fun unaryMinus(): DifferentiableDouble {
        return DifferentiableDouble(0.0).minus(this)
    }

    override fun minus(value: Double): DifferentiableDouble {
        return minus(DifferentiableDouble(value))
    }

    var value: Double = 0.0
        get() {
            updateValue()
            return field
        }

    var delta: Double = 0.0
    protected var children = mutableMapOf<DifferentiableDouble, Double>()


    constructor(d : Double) {
        value = d
    }

    constructor() {
        value = 0.0
    }


    fun startFwdAutoDiff() {
        this.delta = 1.0
        propagateFwdAutoDiff()
    }

    fun clearFwdAutoDiff() {
        this.delta = 0.0
        propagateFwdAutoDiff()
    }


    fun propagateFwdAutoDiff() {
        children.forEach { (c, _) -> c.fwdAutoDiff() }
    }

    fun startRevAutoDiff(delta: Double = 1.0) {
        this.delta = delta
        propagateRevAutoDiff()
    }

    fun revAutoDiff(child: DifferentiableDouble, delta: Double) {
        children.put(child, delta)
        this.delta = children.map { (_, d) -> d }.sum()
        propagateRevAutoDiff()
    }


    fun addChild(child: DifferentiableDouble) {
        children.put(child, 0.0)
    }

    override operator fun plus(that: DifferentiableDouble): DifferentiableDouble {
        return Plus(this, that)
    }

    override operator fun minus(that: DifferentiableDouble): DifferentiableDouble {
        return Minus(this, that)
    }

    override operator fun times(that: DifferentiableDouble): DifferentiableDouble {
        return Times(this, that)
    }

    override operator fun div(that: DifferentiableDouble): DifferentiableDouble {
        return Div(this, that)
    }

    open fun updateValue() {
    }

    open fun fwdAutoDiff() {
        propagateFwdAutoDiff()
    }

    open fun propagateRevAutoDiff() {
    }

}
