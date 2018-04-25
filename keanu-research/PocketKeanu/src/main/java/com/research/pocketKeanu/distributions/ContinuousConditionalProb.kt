package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.randomFactory.DoubleLikeRandomFactory

interface ContinuousConditionalProb {
    fun <T : DoubleLike<T>> logProb(randVars: DoubleLikeRandomFactory<T>, givens: List<T>): T
}
