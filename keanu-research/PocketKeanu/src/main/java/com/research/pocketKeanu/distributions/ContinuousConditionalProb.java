package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.autoDiff.DifferentiableDouble;

public interface ContinuousConditionalProb {
    DifferentiableDouble logProb(DifferentiableDouble [] randVars, DifferentiableDouble [] givens);
}
