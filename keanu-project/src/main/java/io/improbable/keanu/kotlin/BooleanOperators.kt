package io.improbable.keanu.kotlin

interface BooleanOperators<IN, T> {

    fun and(vararg those: IN): T

    fun or(vararg those: IN): T

}
