package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.ADouble;
import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.abstractTypes.IntLike;
import io.improbable.keanu.distributions.continuous.Gaussian;
import io.improbable.keanu.distributions.continuous.Uniform;

import static com.research.pocketKeanu.abstractTypes.PrefixOperationsKt.pow;
import static java.lang.Math.log;

public class Random implements RandomFactory<ADouble,AInt> {

    private java.util.Random random = new java.util.Random();
    private ADouble logProb = new ADouble(0.0);
    static final double logSqrt2PI = Math.log(Math.sqrt(2.0*Math.PI));

    @Override
    public ADouble nextConstant(double value) {
        return new ADouble(value);
    }

    @Override
    public ADouble getLogProb() {
        return logProb;
    }

    @Override
    public void setLogProb(double value) {
        logProb = new ADouble(value);
    }

    @Override
    public ADouble nextGaussian() {
        double x = random.nextGaussian();
        logProb = logProb.minus(x*x/2.0 + logSqrt2PI);
        return new ADouble(x);
    }

    @Override
    public ADouble nextGaussian(ADouble mu, ADouble sigma) {
        ADouble x = new ADouble(random.nextGaussian());
        logProb = logProb.minus(pow(x.minus(mu).div(sigma), 2.0).div(2.0).plus(logSqrt2PI).plus(sigma));
        return x;
    }


    @Override
    public AInt nextInt() {
        return new AInt(random.nextInt());
    }
}
