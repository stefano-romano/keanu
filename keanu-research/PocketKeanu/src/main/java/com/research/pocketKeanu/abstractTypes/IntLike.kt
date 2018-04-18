package com.research.pocketKeanu.abstractTypes

public interface IntLike<T> : ArithmeticOperators<T> {
    operator fun minus(value: Int): T
    operator fun plus(value: Int): T
    operator fun times(value: Int): T
    operator fun div(value: Int): T
    operator fun unaryMinus() : T

}