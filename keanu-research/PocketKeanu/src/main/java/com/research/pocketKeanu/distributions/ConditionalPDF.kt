package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.autoDiff.DifferentiableDouble

class ConditionalPDF(val jointPDF : PDF, val observations : Map<DifferentiableDouble, Double>) : PDF {
    override fun getVars(): List<DifferentiableDouble> {
        return jointPDF.getVars().filter({vertex -> !observations.containsKey(vertex)})
    }

    override fun logProb(randVars: Map<DifferentiableDouble, Double>): DifferentiableDouble {
        return jointPDF.logProb(observations + randVars)
    }

}