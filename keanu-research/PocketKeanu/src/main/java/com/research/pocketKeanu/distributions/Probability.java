package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface Probability<R, T> {
    T logProb(R randVars);
}
