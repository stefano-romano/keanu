package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike

interface Probability<R, T> {
    fun logProb(randVars: R): T
}
