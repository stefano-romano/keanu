package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.ADouble;

import java.util.Random;

public class GaussianVertex extends DoubleVertex<ADouble> implements ProbabilisticVertex<Double> {
    Vertex<ADouble> mu;
    Vertex<ADouble> sigma;

    public GaussianVertex(Vertex<ADouble> mu, Vertex<ADouble> sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    @Override
    public void run() {
        value = new ADouble(new Random().nextGaussian()).times(sigma.getValue()).plus(mu.getValue());
    }

    public Double logProb() {
        return new org.apache.commons.math3.analysis.function.Gaussian(mu.getValue().getValue(), sigma.getValue().getValue()).value(getValue().getValue());
    }
}
