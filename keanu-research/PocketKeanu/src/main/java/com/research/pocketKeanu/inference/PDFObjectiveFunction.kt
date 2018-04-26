package com.research.pocketKeanu.inference

import com.research.pocketKeanu.autoDiff.DifferentiableDouble
import com.research.pocketKeanu.distributions.PDF
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient

class PDFObjectiveFunction(val pdf : PDF) {
    val params = constructParams()

    fun fitness() : ObjectiveFunction {
        return ObjectiveFunction({
            doubles ->
            var i = 0
            for(e in params.entries) {
                e.setValue(doubles[i++])
            }
            val fitness = pdf.logProb(params).value
            fitness
        })
    }

    fun gradient() : ObjectiveFunctionGradient {
        return ObjectiveFunctionGradient({
            doubles ->
            var i = 0

            for(e in params.entries) {
                e.setValue(doubles[i++])
            }
            val logP = pdf.logProb(params)
            logP.delta = 1.0
            logP.propagateRevAutoDiff()
            val grad = DoubleArray(doubles.size)
            i = 0
            for(e in params.keys) {
                grad[i++] = e.delta
            }
            grad
        })
    }

    fun constructParams() : MutableMap<DifferentiableDouble, Double> {
        val map = HashMap<DifferentiableDouble,Double>()
        for(v in pdf.getVars()) {
            map.put(v,v.value)
        }
        return map
    }
}