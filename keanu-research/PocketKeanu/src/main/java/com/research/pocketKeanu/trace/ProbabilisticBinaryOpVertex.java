package com.research.pocketKeanu.trace;

import java.util.function.BinaryOperator;

public class ProbabilisticBinaryOpVertex<T,R> extends BinaryOpVertex<T> implements ProbabilisticVertex<R> {
    public ProbabilisticBinaryOpVertex(BinaryOperator<T> op, Vertex<T> a, Vertex<T> b) {
        super(op, a, b);
    }

    @Override
    public R logProb() {
        return null;
    }
}
