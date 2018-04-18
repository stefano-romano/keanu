package com.research.pocketKeanu;

import com.research.pocketKeanu.randomFactory.ProbabilisticRandom;
import com.research.pocketKeanu.trace.Vertex;

public class Test {

    @org.junit.Test
    public void test() {
        ProbabilisticRandom rand = new ProbabilisticRandom();

        Vertex<?> v = rand.nextGaussian();
        v.run();
        System.out.println(v.getValue());
    }
}
