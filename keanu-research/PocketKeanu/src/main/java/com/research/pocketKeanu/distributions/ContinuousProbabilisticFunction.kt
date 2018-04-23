package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory
import java.util.function.BiFunction

interface ContinuousProbabilisticFunction {
    fun <T : DoubleLike<T>> apply(`in`: List<T>, rand: ContinuousRandomFactory<T>): List<T>
}
