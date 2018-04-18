package com.research.pocketKeanu.trace;

import java.util.ArrayList;
import java.util.List;

public abstract class Vertex<T> implements Runnable {
    T value;
    ArrayList<Vertex<?>> children;

    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    public void addChild(Vertex<?> child) {
        this.children.add(child);
    }

    public List<Vertex<?>> getChildren() {
        return this.children;
    }

}
