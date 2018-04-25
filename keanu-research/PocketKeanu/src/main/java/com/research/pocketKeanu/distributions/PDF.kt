package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.autoDiff.DifferentiableDouble

interface PDF : Probability<Map<DifferentiableDouble, Double>, DifferentiableDouble> {
    fun getVars() : List<DifferentiableDouble>
}
