package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface ConditionalProb<T extends DoubleLike<T>, RANDVARS, GIVENS> {
    double logProb(RANDVARS randVars, GIVENS givens);
    RANDVARS sample(GIVENS givens);
}
