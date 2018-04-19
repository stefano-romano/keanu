package com.research.pocketKeanu.abstractTypes

fun <T : DoubleLike<T>> log(x : T) : T {
    return x.log()
}