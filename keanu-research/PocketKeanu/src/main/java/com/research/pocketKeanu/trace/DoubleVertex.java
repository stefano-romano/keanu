package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import org.jetbrains.annotations.NotNull;

public abstract class DoubleVertex<T extends DoubleLike<T>> extends Vertex<T> implements DoubleLike<DoubleVertex<T>> {

    @NotNull
    @Override
    public DoubleVertex<T> minus(double value) {
        return null;
    }

    @NotNull
    @Override
    public DoubleVertex<T> plus(double value) {
        return null;
    }

    @NotNull
    @Override
    public DoubleVertex<T> times(double value) {
        return null;
    }

    @NotNull
    @Override
    public DoubleVertex<T> div(double value) {
        return null;
    }

    @NotNull
    @Override
    public DoubleVertex<T> unaryMinus() {
        return null;
    }

    @Override
    public DoubleVertex<T> minus(DoubleVertex<T> that) {
        return null;
    }

    @Override
    public DoubleVertex<T> plus(DoubleVertex<T> that) {
        return null;
    }

    @Override
    public DoubleVertex<T> times(DoubleVertex<T> that) {
        return null;
    }

    @Override
    public DoubleVertex<T> div(DoubleVertex<T> that) {
        return null;
    }

}
