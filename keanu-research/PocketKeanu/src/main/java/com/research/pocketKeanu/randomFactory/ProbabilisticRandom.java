package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.ADouble;
import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.autoDiff.DifferentiableDouble;
import com.research.pocketKeanu.distributions.Gaussian;
import com.research.pocketKeanu.trace.*;
import io.improbable.keanu.distributions.continuous.Beta;

import java.util.ArrayList;
import java.util.Random;

public class ProbabilisticRandom implements RandomFactory<DifferentiableDouble, AInt> {

    private ArrayList<DifferentiableDouble> probabiliticDoubles = new ArrayList<>();
    private DifferentiableDouble masterP = new DifferentiableDouble(0.0);

    @Override
    public DifferentiableDouble nextGaussian(DifferentiableDouble mu, DifferentiableDouble sigma) {
        DifferentiableDouble x = new DifferentiableDouble(mu.getValue());
        DifferentiableDouble logP = Gaussian.Companion.logProb(mu, sigma, x);
        masterP = masterP.plus(logP);
        probabiliticDoubles.add(x);
        return x;
    }

    @Override
    public DifferentiableDouble nextGaussian(double mu, double sigma) {
        return nextGaussian(new DifferentiableDouble(mu), new DifferentiableDouble(sigma));
    }

    @Override
    public DifferentiableDouble nextConstant(double value) {
        return new DifferentiableDouble(value);
    }

    @Override
    public AInt nextInt() {
        return null;
    }
}
