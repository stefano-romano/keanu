package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.ADouble;
import com.research.pocketKeanu.abstractTypes.AInt;
import com.research.pocketKeanu.abstractTypes.IntLike;
import io.improbable.keanu.distributions.continuous.Gaussian;
import io.improbable.keanu.distributions.continuous.Uniform;

public class Random implements ContinuousRandomFactory<ADouble>, DiscreteRandomFactory<AInt> {

    private java.util.Random random = new java.util.Random();

    public void setRandom(java.util.Random random) {
        this.random = random;
    }

    @Override
    public ADouble nextDouble(double min, double max) {
        double randomDouble = Uniform.sample(min, max, random);
        return new ADouble(randomDouble);
    }

    @Override
    public ADouble nextGaussian(ADouble mu, ADouble sigma) {
        double randomDouble = Gaussian.sample(mu.getValue(), sigma.getValue(), random);
        return new ADouble(randomDouble);
    }

    @Override
    public ADouble nextGaussian(double mu, ADouble sigma) {
        double randomDouble = Gaussian.sample(mu, sigma.getValue(), random);
        return new ADouble(randomDouble);
    }

    @Override
    public ADouble nextGaussian(ADouble mu, double sigma) {
        double randomDouble = Gaussian.sample(mu.getValue(), sigma, random);
        return new ADouble(randomDouble);
    }

    @Override
    public ADouble nextGaussian(double mu, double sigma) {
        double randomDouble = Gaussian.sample(mu, sigma, random);
        return new ADouble(randomDouble);
    }

    @Override
    public AInt nextInt() {
        return new AInt(random.nextInt());
    }
}
