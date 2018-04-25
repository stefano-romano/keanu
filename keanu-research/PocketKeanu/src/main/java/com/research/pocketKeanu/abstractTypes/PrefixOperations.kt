package com.research.pocketKeanu.abstractTypes

fun <T : DoubleLike<T>> log(x : T) : T {
    return x.log()
}

fun <T : DoubleLike<T>> pow(base : T, exponent : T) : T {
    return base.pow(exponent)
}

fun <T : DoubleLike<T>> pow(base : T, exponent : Double) : T {
    return base.pow(exponent)
}
