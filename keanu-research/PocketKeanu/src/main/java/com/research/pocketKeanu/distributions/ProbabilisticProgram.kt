package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import com.research.pocketKeanu.randomFactory.DifferentiableRandom
import com.research.pocketKeanu.randomFactory.DoubleLikeRandomFactory
import com.research.pocketKeanu.randomFactory.Recorder

class ProbabilisticProgram : GraphicalProbability {
    val model : ContinuousProbabilisticFunction
    val inputs : List<DifferentiableDouble>
    val outputs : List<DifferentiableDouble>

    constructor(val model : ContinuousProbabilisticFunction, val inputs : List<DifferentiableDouble>) {
        this.model = model
        this.inputs = inputs
        val rand = Recorder(DifferentiableRandom())
        this.outputs = model.apply(inputs, rand)

    }


    override fun logProb(randVars: MutableMap<DifferentiableDouble, Double>?): DifferentiableDouble {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun <T : DoubleLike<T>> logProb(randVars: DoubleLikeRandomFactory<T>, givens: List<T>): T {
//        randVars.setLogProb(0.0)
//        model.apply(givens, randVars)
//        return randVars.logProb
//    }
//
//    override fun <T : DoubleLike<T>> apply(input: List<T>, rand: DoubleLikeRandomFactory<T>): List<T> {
//        return model.apply(input, rand)
//    }

}
