package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.ADouble;
import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.abstractTypes.IntLike;
import io.improbable.keanu.distributions.continuous.Gaussian;
import io.improbable.keanu.distributions.continuous.Uniform;

import static java.lang.Math.log;

public class Random implements RandomFactory<ADouble,AInt> {

    private java.util.Random random = new java.util.Random();
    private double logProb = 0.0;
    static final double logSqrt2PI = Math.log(Math.sqrt(2.0*Math.PI));

    @Override
    public ADouble nextGaussian(ADouble mu, ADouble sigma) {
        return new ADouble(random.nextGaussian()*sigma.getValue() + mu.getValue());
    }

    @Override
    public ADouble nextGaussian(double mu, double sigma) {
        return new ADouble(random.nextGaussian()*sigma + mu);
    }

    @Override
    public ADouble nextConstant(double value) {
        return new ADouble(value);
    }

    @Override
    public double getLogProb() {
        return logProb;
    }

    @Override
    public void setLogProb(Double value) {
        logProb = value;
    }

    @Override
    public ADouble nextGaussian() {
        double x = random.nextGaussian();
        logProb += -x*x/2.0 - logSqrt2PI;
        return new ADouble(x);
    }

    @Override
    public AInt nextInt() {
        return new AInt(random.nextInt());
    }
}
