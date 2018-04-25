package com.research.pocketKeanu.trace;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.abstractTypes.IntLike;

import java.util.ArrayList;
import java.util.List;

public class Trace<D extends DoubleLike<D>,I extends IntLike<I>> {
    ArrayList<D> doubles = new ArrayList<>();
    ArrayList<I> ints = new ArrayList<>();
    int doubleCounter = 0;
    int intCounter = 0;



    public List<D> getDoubles() {
        return doubles;
    }

    public void setDoubles(ArrayList<D> doubles) {
        this.doubles = doubles;
    }

    public List<I> getInts() {
        return ints;
    }

    public void setInts(ArrayList<I> ints) {
        this.ints = ints;
    }

    public boolean add(D d) {
        return doubles.add(d);
    }

    public boolean add(I i) {
        return ints.add(i);
    }

    public void clear() {
        rewind();
        doubles.clear();
        ints.clear();
    }

    public void rewind() {
        doubleCounter = 0;
        intCounter = 0;
    }

    public D nextDouble() {
        if(doubleCounter >= doubles.size()) throw(new IndexOutOfBoundsException());
        return doubles.get(doubleCounter++);
    }

    public I nextInt() {
        if(intCounter >= ints.size()) throw(new IndexOutOfBoundsException());
        return ints.get(intCounter++);
    }
}
