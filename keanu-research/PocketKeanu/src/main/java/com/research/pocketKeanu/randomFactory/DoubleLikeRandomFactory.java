package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface DoubleLikeRandomFactory<T extends DoubleLike<T>> {
    T nextGaussian();
    T nextConstant(double value);
    T getLogProb();
    void setLogProb(double value);

    default T nextGaussian(T mu,T sigma) {return nextGaussian().times(sigma).plus(mu); }
    default T nextGaussian(double mu,double sigma) {return nextGaussian(nextConstant(mu), nextConstant(sigma)); }
}
