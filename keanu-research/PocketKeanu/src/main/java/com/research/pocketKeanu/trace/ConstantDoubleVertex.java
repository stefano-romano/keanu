package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.DoubleLike;

public class ConstantDoubleVertex<T extends DoubleLike<T>> extends DoubleVertex<T> {

    public ConstantDoubleVertex(T value) {
        setValue(value);
    }

    @Override
    public void run() {
    }
}
