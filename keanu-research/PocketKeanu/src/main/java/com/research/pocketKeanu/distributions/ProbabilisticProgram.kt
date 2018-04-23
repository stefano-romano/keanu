package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.ADouble
import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory

class ProbabilisticProgram(var model: ContinuousProbabilisticFunction) : ContinuousConditionalProb, ContinuousProbabilisticFunction {


    override fun <T : DoubleLike<T>> logProb(randVars: ContinuousRandomFactory<T>, givens: List<T>): T {
        randVars.setLogProb(0.0)
        model.apply(givens, randVars)
        return randVars.logProb
    }

    override fun <T : DoubleLike<T>> apply(input: List<T>, rand: ContinuousRandomFactory<T>): List<T> {
        return model.apply(input, rand)
    }

}
