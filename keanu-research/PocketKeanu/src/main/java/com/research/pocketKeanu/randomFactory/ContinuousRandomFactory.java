package com.research.pocketKeanu.randomFactory;


import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface ContinuousRandomFactory<T extends DoubleLike<T>> {

//    T nextDouble(double min, double max);
    T nextGaussian(T mu, T sigma);
//    T nextGaussian(double mu, T sigma);
//    T nextGaussian(T mu, double sigma);
    T nextGaussian(double mu, double sigma);

    default T nextGaussian() {
        return nextGaussian(0.0, 1.0);
    }

}
