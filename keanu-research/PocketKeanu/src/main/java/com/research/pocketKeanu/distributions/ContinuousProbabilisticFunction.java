package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory;

import java.util.List;
import java.util.function.BiFunction;

public interface ContinuousProbabilisticFunction {
    <T extends DoubleLike<T>> List<T> apply(List<T> in, ContinuousRandomFactory<T> rand);
}
