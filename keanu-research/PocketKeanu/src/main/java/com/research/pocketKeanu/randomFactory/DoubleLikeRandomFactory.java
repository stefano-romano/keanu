package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface DoubleLikeRandomFactory<T extends DoubleLike<T>> {
    T nextConstant(double value);
    T getLogProb();
    void setLogProb(double value);
    T nextGaussian(T mu,T sigma);

    default T nextGaussian() {return nextGaussian(0.0, 1.0);}
    default T nextGaussian(double mu,double sigma) {return nextGaussian(nextConstant(mu), nextConstant(sigma)); }
    default T nextGaussian(T mu,double sigma) {return nextGaussian(mu, nextConstant(sigma)); }
    default T nextGaussian(double mu, T sigma) {return nextGaussian(nextConstant(mu), sigma); }
}
