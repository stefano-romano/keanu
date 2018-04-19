package com.research.pocketKeanu.distributions;

import com.research.pocketKeanu.autoDiff.DifferentiableDouble;

public interface ContinuousJointProb {
    DifferentiableDouble logProb(DifferentiableDouble [] randVars);
    //double logProb(double [] randVars);
}
