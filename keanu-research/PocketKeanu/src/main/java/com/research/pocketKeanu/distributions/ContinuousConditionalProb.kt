package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.ADouble
import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory

interface ContinuousConditionalProb {
    fun <T : DoubleLike<T>> logProb(randVars: ContinuousRandomFactory<T>, givens: List<T>): T
}
