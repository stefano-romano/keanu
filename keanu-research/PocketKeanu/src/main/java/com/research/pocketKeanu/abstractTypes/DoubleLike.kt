package com.research.pocketKeanu.abstractTypes

interface DoubleLike<T : DoubleLike<T>> : ArithmeticOperators<T> {
    operator fun minus(value: Double): T
    operator fun plus(value: Double): T
    operator fun times(value: Double): T
    operator fun div(value: Double): T
    operator fun unaryMinus(): T
    fun log() : T
    fun pow(exponent: T) : T
    fun pow(exponent: Double) : T
}