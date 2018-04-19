package com.research.pocketKeanu;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.abstractTypes.IntLike;
import com.research.pocketKeanu.randomFactory.RandomFactory;

public interface Model {
    public <T extends DoubleLike<T>,I extends IntLike<I>> T[] apply(T[] doubleParams, I[] intParams, RandomFactory<T,I> rand);
}
