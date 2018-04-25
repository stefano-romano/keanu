package com.research.pocketKeanu.inference

import com.research.pocketKeanu.distributions.PDF
import org.apache.commons.math3.optim.InitialGuess
import org.apache.commons.math3.optim.MaxEval
import org.apache.commons.math3.optim.SimpleValueChecker
import org.apache.commons.math3.optim.nonlinear.scalar.gradient.NonLinearConjugateGradientOptimizer

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType.MAXIMIZE


fun PDF.findMAP(maxEvaluations : Int) {
    val objective = PDFObjectiveFunction(this)
    val optimizer = NonLinearConjugateGradientOptimizer(
            NonLinearConjugateGradientOptimizer.Formula.POLAK_RIBIERE,
            SimpleValueChecker(1e-8, 1e-8)
    )

    val startPoint = objective.params.values.toDoubleArray()

    val optimal = optimizer.optimize(
            MaxEval(maxEvaluations),
            objective.fitness(),
            objective.gradient(),
            MAXIMIZE,
            InitialGuess(startPoint)
    )
    getVars().forEachIndexed {
        index, dd ->
        dd.value = optimal.point[index]
    }
}