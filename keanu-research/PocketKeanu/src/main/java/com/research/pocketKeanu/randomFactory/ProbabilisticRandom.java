package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.ADouble;
import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.trace.*;

import java.util.Random;

public class ProbabilisticRandom implements ContinuousRandomFactory<DoubleVertex<ADouble>>, DiscreteRandomFactory<AInt> {

    private Random random = new Random();
    private Trace trace = new Trace();

    @Override
    public DoubleVertex<ADouble> nextDouble(double min, double max) {
        return null;
    }

    @Override
    public DoubleVertex<ADouble> nextGaussian(DoubleVertex<ADouble> mu, double sigma) {
        return null;
    }

    @Override
    public DoubleVertex<ADouble> nextGaussian(double mu, DoubleVertex<ADouble> sigma) {
        return nextGaussian(new ConstantDoubleVertex<>(new ADouble(mu)), sigma);
    }

    @Override
    public DoubleVertex<ADouble> nextGaussian(DoubleVertex<ADouble> mu, DoubleVertex<ADouble> sigma) {
        DoubleVertex<ADouble> v = new GaussianVertex(mu, sigma);
        trace.add(v);
        return(v);
    }

    @Override
    public DoubleVertex<ADouble> nextGaussian(double mu, double sigma) {
        return nextGaussian(new ConstantDoubleVertex<>(new ADouble(mu)), new ConstantDoubleVertex<>(new ADouble(sigma)));
    }

    @Override
    public AInt nextInt() {
        return null;
    }
}
