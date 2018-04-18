package com.research.pocketKeanu.conditionalProb;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public interface ConditionalProb<T extends DoubleLike<T>, RANDVARS, GIVENS> {
    DoubleLike<T> logProb(RANDVARS randVars, GIVENS givens);
    RANDVARS sample(GIVENS givens);
}
