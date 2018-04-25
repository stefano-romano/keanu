package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.autoDiff.DifferentiableDouble;
import io.improbable.keanu.distributions.continuous.Beta;


import static com.research.pocketKeanu.abstractTypes.PrefixOperationsKt.pow;

public class DifferentiableRandom implements RandomFactory<DifferentiableDouble, AInt> {

    private DifferentiableDouble logProb = new DifferentiableDouble(0.0);
    static final double logSqrt2PI = Math.log(Math.sqrt(2.0*Math.PI));

    @Override
    public DifferentiableDouble nextConstant(double value) {
        return new DifferentiableDouble(value);
    }

    @Override
    public DifferentiableDouble getLogProb() {
        return logProb;
    }

    @Override
    public void setLogProb(double value) {
        logProb = new DifferentiableDouble(value);
    }

    @Override
    public DifferentiableDouble nextGaussian() {
        DifferentiableDouble x = new DifferentiableDouble(0.0);
        logProb = logProb.minus(x.times(x).div(2.0).plus(logSqrt2PI));
        return x;
    }

    @Override
    public DifferentiableDouble nextGaussian(DifferentiableDouble mu, DifferentiableDouble sigma) {
        DifferentiableDouble x = new DifferentiableDouble(mu.getValue());
        logProb = logProb.minus(pow(x.minus(mu).div(sigma), 2.0).div(2.0).plus(logSqrt2PI).plus(sigma));
        return x;
    }

    @Override
    public AInt nextInt() {
        return new AInt(0);
    }
}
