package com.research.pocketKeanu;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory;
import com.research.pocketKeanu.randomFactory.ProbabilisticRandom;
import com.research.pocketKeanu.trace.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Test implements ContinuousModel {

    @org.junit.Test
    public void test() {
        ProbabilisticRandom rand = new ProbabilisticRandom();

        Vertex<?> v = rand.nextGaussian();
        v.run();
        System.out.println(v.getValue());
    }

    @Override
    public <T extends DoubleLike<T>> List<T> apply(T[] doubleParams, ContinuousRandomFactory<T> rand) {
        T temp = rand.nextGaussian();
        List<T> result = new ArrayList<T>();
        result.add(temp);

        return result;
    }
}
