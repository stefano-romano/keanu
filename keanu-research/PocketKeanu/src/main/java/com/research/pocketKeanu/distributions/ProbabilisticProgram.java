package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.autoDiff.DifferentiableDouble;
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory;

import java.util.List;

public class ProbabilisticProgram implements ContinuousConditionalProb, ContinuousProbabilisticFunction {

    ContinuousProbabilisticFunction fn;

    public ProbabilisticProgram(ContinuousProbabilisticFunction function) {
        fn = function;
    }

    @Override
    public DifferentiableDouble logProb(DifferentiableDouble[] randVars, DifferentiableDouble[] givens) {
        return null;
    }

    @Override
    public <T extends DoubleLike<T>> List<T> apply(List<T> in, ContinuousRandomFactory<T> rand) {
        return fn.apply(in, rand);
    }
}
