package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.AInt;

import java.util.Random;

import static java.lang.Math.log;

public class UniformIntVertex extends Vertex<AInt> implements ProbabilisticVertex<Double> {

    @Override
    public void run() {
        setValue(new AInt(new Random().nextInt()));
    }

    @Override
    public Double logProb() {
        return(-Integer.BYTES*log(256.0));
    }
}
