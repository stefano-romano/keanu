package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.AInt
import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import com.research.pocketKeanu.randomFactory.DifferentiableRandom
import com.research.pocketKeanu.randomFactory.Recorder
import com.research.pocketKeanu.trace.Trace

class ProbabilisticProgram : PDF {

    val model : ContinuousProbabilisticFunction
    val inputs : List<DifferentiableDouble>
    val outputs : List<DifferentiableDouble>
    val trace : Trace<DifferentiableDouble, AInt>
    val logP : DifferentiableDouble

    constructor(model : ContinuousProbabilisticFunction, inputs : List<DifferentiableDouble>) {
        this.model = model
        this.inputs = inputs
        val rand = Recorder(DifferentiableRandom())
        this.outputs = model.apply(inputs, rand)
        trace = rand.trace
        logP = rand.logProb
    }

    fun observe(observations : Map<DifferentiableDouble, Double>) : ConditionalPDF {
        return ConditionalPDF(this, observations)
    }

    override fun logProb(randVars: Map<DifferentiableDouble, Double>): DifferentiableDouble {
        var valchange = false
        randVars.forEach { (vertex, value) ->
            if(vertex.value != value) {
                valchange = true
                vertex.value = value
            }
        }
        if(valchange) logP.updateValue()
        return logP
    }

    override fun getVars(): List<DifferentiableDouble> {
        return inputs + trace.doubles
    }

}
