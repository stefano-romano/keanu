package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.randomFactory.DoubleLikeRandomFactory

interface ContinuousProbabilisticFunction {
    fun <T : DoubleLike<T>> apply(inputs : List<T>, rand: DoubleLikeRandomFactory<T>): List<T>
}
