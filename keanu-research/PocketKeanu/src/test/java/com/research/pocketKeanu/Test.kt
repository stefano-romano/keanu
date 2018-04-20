package com.research.pocketKeanu

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.distributions.ContinuousProbabilisticFunction
import com.research.pocketKeanu.distributions.ProbabilisticProgram
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory
import com.research.pocketKeanu.randomFactory.ProbabilisticRandom

import java.util.ArrayList

class Test : ContinuousProbabilisticFunction {

    @org.junit.Test
    fun test() {
        val rand = ProbabilisticRandom()
        val probProgram = ProbabilisticProgram(this);

        println("Hello")
    }

    override fun <T : DoubleLike<T>> apply(doubleParams: List<T>, rand: ContinuousRandomFactory<T>): List<T> {
        val temp = rand.nextGaussian()
        val result = ArrayList<T>()
        result.add(temp)

        return result
    }
}
