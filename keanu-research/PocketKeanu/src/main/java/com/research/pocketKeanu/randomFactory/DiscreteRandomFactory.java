package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.IntLike;

public interface DiscreteRandomFactory<T extends IntLike<T>> {
    T nextInt();
}
