package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.abstractTypes.IntLike;

import java.util.function.BinaryOperator;

public class BinaryOpVertex<T> extends Vertex<T> {


    protected final Vertex<T> a;
    protected final Vertex<T> b;
    protected final BinaryOperator<T> op;

    public BinaryOpVertex(BinaryOperator<T> op, Vertex<T> a, Vertex<T> b) {
        this.a = a;
        this.b = b;
        this.op = op;

    }

    @Override
    public void run() {
        setValue(op.apply(a.getValue(),b.getValue()));
    }



}

